package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.imgrocket.imgrocket.tool.Function
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_choose_single_photo.*

class ChooseSinglePhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_single_photo)
        Function.black(this)
        single_layout_add.setOnClickListener {
            Matisse.from(this@ChooseSinglePhotoActivity)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                    .countable(false)
                    .theme(R.style.Matisse_Custom)
                    .maxSelectable(1)
                    .capture(false)
                    .imageEngine(PicassoEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }
        Matisse.from(this@ChooseSinglePhotoActivity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
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
            single_text_show.text = result.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 23
    }
}