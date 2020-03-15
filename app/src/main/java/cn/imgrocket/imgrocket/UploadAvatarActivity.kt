package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityUploadAvatarBinding
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.APP.Companion.context
import cn.imgrocket.imgrocket.tool.BitmapUtil.bitmap2FileCache
import cn.imgrocket.imgrocket.tool.BitmapUtil.load
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.URL
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException


class UploadAvatarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadAvatarBinding
    private lateinit var global: APP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        global = application as APP
        binding = ActivityUploadAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Matisse.from(this@UploadAvatarActivity)
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
            if (result.isEmpty()) return
            val avatar = load(this, result[0])
            post(URL.uploadAvatarURL, global.uid!!, global.token!!, bitmap2FileCache(context, avatar!!, 85), object : Callback {
                override fun onResponse(result: String?) {
                    toast(result!!)
                }

            })
        }
    }

    private fun post(url: String, uid: String, token: String, portrait: File): String? {
        val okHttpClient = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        val body: RequestBody = portrait.asRequestBody("image/*".toMediaTypeOrNull())
        val filename: String = portrait.name
        requestBody.addFormDataPart("portrait", filename, body)
        requestBody.addFormDataPart("uid", uid)
        requestBody.addFormDataPart("token", token)
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

    private fun post(url: String, uid: String, token: String, portrait: File, callback: Callback) {
        val handler = Handler()
        Thread(Runnable {
            val result = post(url, uid, token, portrait)
            handler.post { callback.onResponse(result) }
        }).start()
    }


    internal interface Callback {
        fun onResponse(result: String?)
    }


    companion object {
        private const val REQUEST_CODE_CHOOSE = 111
    }
}
