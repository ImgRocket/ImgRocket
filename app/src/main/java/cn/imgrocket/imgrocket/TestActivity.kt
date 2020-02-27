package cn.imgrocket.imgrocket

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.Function.post

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val buttonSubmit = findViewById<Button>(R.id.test_button_submit)
        val textViewView = findViewById<TextView>(R.id.test_textview_view)
        val editTextInput = findViewById<EditText>(R.id.test_edit_input)
        buttonSubmit.setOnClickListener {
            post("https://api.huhaorui.com/test.php", editTextInput.text.toString(), object : Function.Callback {
                override fun onResponse(result: String?) {
                    textViewView.text = result
                }
            })
        }
        //我希望，在textViewView上显示出返回的东西
    }
}