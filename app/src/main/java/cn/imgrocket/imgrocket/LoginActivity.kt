package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityLoginBinding
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.getSalt
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Function_Java.salt
import cn.imgrocket.imgrocket.tool.LoginResult
import cn.imgrocket.imgrocket.tool.Message
import cn.imgrocket.imgrocket.tool.Status
import cn.imgrocket.imgrocket.tool.URL
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        black(this)
        val locale: String = Locale.getDefault().language
        if (locale != "zh") binding.loginTextTip.textSize = 18F
        binding.loginButtonLogin.setOnClickListener {
            val account = binding.loginEditAccount.text.toString()
            val password = binding.loginEditPassword.text.toString()
            if (password.length < 6) {
                toast("密码需要至少六位")
                return@setOnClickListener
            }
            post(URL.loginURL, account, salt(password, getSalt()), object : Callback {
                override fun onResponse(result: String?) {
                    if (result != null) {
//                        toast(result)
                        val message = Message.parse(result, object : TypeToken<Message<LoginResult>>() {})
//                        Log.d(javaClass.name, message.message)
                        when (message.status) {
                            Status.OK -> {
                                Log.d(javaClass.name, "登录成功")
                                message.data?.let {
                                    Log.d(javaClass.name, "LoginResult: ${it.uid} ${it.username} ${it.token}")
                                }
                            }
                            Status.UPE -> Log.d(javaClass.name, "密码错误")
                            Status.UNE -> Log.d(javaClass.name, "用户不存在")
                            else -> Log.d(javaClass.name, "其他错误")
                        }

                    }
                }
            })
        }
        binding.loginImgAlipay.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.loginImgQq.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.loginImgWechat.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.loginTextCreate.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        binding.loginTextForget.setOnClickListener {
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