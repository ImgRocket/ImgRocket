package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.getSalt
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Function_Java.salt
import cn.imgrocket.imgrocket.tool.URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        black(this)
        val locale: String = Locale.getDefault().language
        if (locale != "zh") login_text_tip.textSize = 18F
        login_button_login.setOnClickListener {
            val account = login_edit_account.text.toString()
            val password = login_edit_password.text.toString()
            if (password.length < 6) {
                toast("密码需要至少六位")
                return@setOnClickListener
            }
            post(URL.loginURL, account, salt(password, getSalt()), object : Callback {
                override fun onResponse(result: String?) {
                    if (result != null) {
                        toast(result)
                    }
                    //TODO 把result解析出来
                }
            })
        }
        login_img_alipay.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        login_img_qq.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        login_img_wechat.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        login_text_create.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        login_text_forget.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
    }

    private fun post(url: String, account: String, password: String): String? {
        val okHttpClient = OkHttpClient()
        val formBody = FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .build()
        val request = Request.Builder()
                .post(formBody)
                .url(url)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body()!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun post(url: String, account: String, password: String, callback: Callback) {
        val handler = Handler()
        Thread(Runnable {
            val result = post(url, account, password)
            handler.post { callback.onResponse(result) }
        }).start()
    }

    internal interface Callback {
        fun onResponse(result: String?)
    }
}