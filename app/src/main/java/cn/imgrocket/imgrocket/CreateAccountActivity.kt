package cn.imgrocket.imgrocket

import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.getSalt
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Function_Java.salt
import cn.imgrocket.imgrocket.tool.URL
import kotlinx.android.synthetic.main.activity_create_account.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        black(this)

        create_button_sign.setOnClickListener {
            val account = create_edit_account.text.toString()
            val password = create_edit_password.text.toString()
            val password2 = create_edit_password2.text.toString()
            if (password.length < 6) {
                toast("密码长度至少为6位")
                return@setOnClickListener
            }
            if (password != password2) {
                toast("两次输入的密码不同")
                return@setOnClickListener
            }
            post(URL.signURL, account, salt(password, getSalt()), object : Callback {
                override fun onResponse(result: String?) {
                    if (result != null) {
                        toast(result)
                    }
                    //TODO 把result解析出来
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


