package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityVipInformationBinding

class VipInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVipInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVipInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}