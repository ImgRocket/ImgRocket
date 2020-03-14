package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}