package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.imgrocket.imgrocket.databinding.ActivityChooseSinglePhotoBinding
import cn.imgrocket.imgrocket.tool.Function
import cn.imgrocket.imgrocket.tool.Function.toast
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine

class ChooseSinglePhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseSinglePhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseSinglePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Function.black(this)
        binding.singleLayoutAdd.setOnClickListener {
            Matisse.from(this@ChooseSinglePhotoActivity)
                    .choose(MimeType.ofImage())
                    .countable(false)
                    .theme(R.style.Matisse_Custom)
                    .maxSelectable(1)
                    .capture(false)
                    .imageEngine(PicassoEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }
        Matisse.from(this@ChooseSinglePhotoActivity)
                .choose(MimeType.ofImage())
                .countable(false)
                .theme(R.style.Matisse_Custom)
                .maxSelectable(1)
                .capture(false)
                .imageEngine(PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            binding.singleTextShow.text = result.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 23
    }
}