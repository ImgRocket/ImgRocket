package cn.imgrocket.imgrocket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        user_layout_item1.setOnClickListener {
            Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
        }
        user_layout_item2.setOnClickListener { }
        user_layout_item3.setOnClickListener { }
    }
}