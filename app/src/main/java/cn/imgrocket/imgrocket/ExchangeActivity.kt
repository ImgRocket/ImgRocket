package cn.imgrocket.imgrocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cn.imgrocket.imgrocket.tool.Function.black

class ExchangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        black(this)
        findViewById<TextView>(R.id.exchange_text_buy).setOnClickListener {


        }
    }
}