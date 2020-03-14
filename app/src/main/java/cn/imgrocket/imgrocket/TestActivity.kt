package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityTestBinding
import cn.imgrocket.imgrocket.tool.Function
import cn.imgrocket.imgrocket.tool.Function.post

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val buttonSubmit = binding.testButtonSubmit
        val textViewView = binding.testTextviewView
        val editTextInput = binding.testEditInput
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