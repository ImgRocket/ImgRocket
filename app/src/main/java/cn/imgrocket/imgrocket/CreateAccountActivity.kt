package cn.imgrocket.imgrocket

import android.os.Bundle

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityCreateAccountBinding
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.getSalt
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Function_Java.salt
import cn.imgrocket.imgrocket.tool.Message
import cn.imgrocket.imgrocket.tool.Status
import cn.imgrocket.imgrocket.tool.URL
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        black(this)
        binding.createEditAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.createButtonSign.isEnabled = binding.createEditAccount.text!!.length <= 20
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.createButtonSign.setOnClickListener {
            val account = binding.createEditAccount.text.toString()
            val password = binding.createEditPassword.text.toString()
            if (password.length < 6) {
                toast("密码长度至少为6位")
                return@setOnClickListener
            }
            post(URL.signURL, account, salt(password, getSalt()), object : Callback {
                override fun onResponse(result: String?) {
                    if (result != null) {
                        toast(result)
                        val message = Message.parse(result, object : TypeToken<Message<String>>() {})
//                        Log.d(javaClass.name, message.message)
                        when (message.status) {
                            Status.OK -> Log.d(javaClass.name, "注册成功")
                            Status.UR -> Log.d(javaClass.name, "用户名已被注册")
                            Status.AIF -> Log.d(javaClass.name, "用户名格式不正确")
                            else -> Log.d(javaClass.name, "其他错误")
                        }
                    }

                }
            })
        }
    }

    private fun post(url: String, account: String, password: String): String? {
        val okHttpClient = OkHttpClient()
        val formBody = FormBody.Builder()
                .add("username", account)
                .add("password", password)
                .build()
        val request = Request.Builder()
                .post(formBody)
                .url(url)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun post(url: String, account: String, password: String, callback: Callback) {
        val handler = Handler()
        Thread {
            val result = post(url, account, password)
            handler.post { callback.onResponse(result) }
        }.start()
    }

    internal interface Callback {
        fun onResponse(result: String?)
    }
}


