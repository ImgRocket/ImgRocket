package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.tool.Function.black
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import kotlinx.android.synthetic.main.activity_choose_multi_photo.*

class ChooseMultiPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_multi_photo)
        black(this)
        Matisse.from(this@ChooseMultiPhotoActivity)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                .countable(true)
                .maxSelectable(9)
                .capture(false)
                .imageEngine(PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            multi_text_show.text = result.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 66
    }
}