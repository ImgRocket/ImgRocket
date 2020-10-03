package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityChooseMultiPhotoBinding
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.APP.Companion.context
import cn.imgrocket.imgrocket.tool.BitmapUtil
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.BitmapUtil.load
import cn.imgrocket.imgrocket.tool.Function
import cn.imgrocket.imgrocket.tool.URL
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class ChooseMultiPhotoActivity : AppCompatActivity() {
    private val global: APP by lazy {
        application as APP
    }
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
                    .maxSelectable(1)
                    .capture(false)
                    .imageEngine(PicassoEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }
        Matisse.from(this@ChooseMultiPhotoActivity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .capture(false)
                .imageEngine(PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            result.forEach {
                val bitmap = load(context, it)
                post(URL.newTaskURL, global.user?.uid!!, global.user?.token!!, BitmapUtil.bitmap2FileCache(context, bitmap!!, 85), object : UploadAvatarActivity.Callback {
                    override fun onResponse(result: String?) {
                        Function.toast(result!!)
                        finish()
                    }
                })
            }
        }
    }

    private fun post(url: String, uid: String, token: String, portrait: File): String? {
        val okHttpClient = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        val body: RequestBody = portrait.asRequestBody("image/*".toMediaTypeOrNull())
        val filename: String = portrait.name
        requestBody.addFormDataPart("imgsource", filename, body)
        requestBody.addFormDataPart("imgsource2", filename, body)
        requestBody.addFormDataPart("userid", uid)
        requestBody.addFormDataPart("usertoken", token)
        requestBody.addFormDataPart("method", "DEN")
        val request = Request.Builder()
                .post(requestBody.build())
                .url(url)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun post(url: String, uid: String, token: String, portrait: File, callback: UploadAvatarActivity.Callback) {
        val handler = Handler()
        Thread {
            val result = post(url, uid, token, portrait)
            handler.post { callback.onResponse(result) }
        }.start()
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 66
    }


}