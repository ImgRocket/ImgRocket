package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.Function.black

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        black(this)
    }
}