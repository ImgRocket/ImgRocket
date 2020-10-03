package cn.imgrocket.imgrocket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.LoginResult
import cn.imgrocket.imgrocket.tool.Message
import cn.imgrocket.imgrocket.tool.URL
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_exchange.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

@SuppressLint("SetTextI18n")
class ExchangeActivity : AppCompatActivity() {
    private val global: APP by lazy { application as APP }
    private var usage by Delegates.observable(Usage("", 0, 0)) { _, _, after ->
        exchange_text_balance.text = "总数${after.all}, 已使用${after.used}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        black(this)

        queryUsage()

        exchange_text_buy.setOnClickListener { }

        exchange_button_submit.setOnClickListener {
            val code = exchange_input_code.text.toString()
            if (code.isNotBlank()) {
                val user = global.user
                if (user == null) {
                    startActivity(Intent(this, LoginActivity::class.java)).also { finish() }
                    return@setOnClickListener
                }
                GlobalScope.launch {
                    post(URL.exchangeCodeURL, hashMapOf("uid" to user.uid, "token" to user.token, "code" to code)) {
                        queryUsage()
                        val result = Message.parse(it, object : TypeToken<Message<Any>>() {}).message
                        Looper.prepare()
                        Toast.makeText(this@ExchangeActivity, result, Toast.LENGTH_SHORT).show()
                        Looper.loop()
                    }
                }
            }
        }
    }

    private fun queryUsage() {
        val user = global.user
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java)).also { finish() }
            return
        }

        GlobalScope.launch {
            post(URL.queryCodeUsageURL, hashMapOf("uid" to user.uid, "token" to user.token)) {
                Log.d(javaClass.name, it)
                val usage: Usage? = Message.parse(it, object : TypeToken<Message<Usage>>() {}).data
                when {
                    usage == null -> Toast.makeText(this@ExchangeActivity, "返回数据错误", Toast.LENGTH_SHORT).show()
                    usage.all == 0 -> {
                        Log.d(javaClass.name, "正在为您申请试用次数")
                        post(URL.trailCodeURL, hashMapOf("uid" to user.uid, "token" to user.token)) { queryUsage() }
                    }
                    else -> this@ExchangeActivity.usage = usage
                }
            }
        }
    }

    companion object {
        fun post(url: String, params: HashMap<String, String>, fail: () -> Unit = {}, success: (String) -> Unit) {
            val okHttpClient = OkHttpClient()
            val formBody = FormBody.Builder().apply { params.entries.forEach { add(it.key, it.value) } }.build()
            val request = Request.Builder().post(formBody).url(url).build()
            try {
                val response = okHttpClient.newCall(request).execute()
                if (response.isSuccessful) response.body?.string()?.let {
                    success.invoke(it)
                    return
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            fail.invoke()
        }
    }


    data class Usage(val user: String, val all: Int, val used: Int)
}