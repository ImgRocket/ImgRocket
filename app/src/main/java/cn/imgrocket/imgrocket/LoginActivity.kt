package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.Function.toast
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        black(this)
        val locale: String = Locale.getDefault().language
        if (locale != "zh") login_text_tip.textSize = 18F
        login_button_login.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))

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
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
        login_text_forget.setOnClickListener {
            toast(resources.getString(R.string.this_function_is_unavailable))
        }
    }
}