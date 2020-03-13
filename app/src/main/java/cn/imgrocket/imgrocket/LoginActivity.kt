package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.getSalt
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Function_Java.salt
import cn.imgrocket.imgrocket.tool.LoginResult
import cn.imgrocket.imgrocket.tool.Message
import cn.imgrocket.imgrocket.tool.Status
import cn.imgrocket.imgrocket.tool.URL
import com.google.gson.reflect.TypeToken
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
//                        toast(result)
                        val message = Message.parse(result, object : TypeToken<Message<LoginResult>>() {})
//                        Log.d(javaClass.name, message.message)
                        when(message.status) {
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