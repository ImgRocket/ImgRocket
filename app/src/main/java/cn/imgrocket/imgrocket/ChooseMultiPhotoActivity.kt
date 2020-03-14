package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityChooseMultiPhotoBinding
import cn.imgrocket.imgrocket.tool.Function.black
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine

class ChooseMultiPhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseMultiPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseMultiPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        black(this)
        binding.multiLayoutAdd.setOnClickListener {
            Matisse.from(this@ChooseMultiPhotoActivity)
                    .choose(MimeType.ofImage())
                    .countable(true)
                    .maxSelectable(99999)
                    .capture(false)
                    .imageEngine(PicassoEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }
        Matisse.from(this@ChooseMultiPhotoActivity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(99999)
                .capture(false)
                .imageEngine(PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            binding.multiTextShow.text = result.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 66
    }


}