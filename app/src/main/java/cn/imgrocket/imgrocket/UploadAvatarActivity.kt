package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.imgrocket.imgrocket.databinding.ActivityUploadAvatarBinding
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.APP.Companion.context
import cn.imgrocket.imgrocket.tool.BitmapUtil
import cn.imgrocket.imgrocket.tool.BitmapUtil.bitmap2FileCache
import cn.imgrocket.imgrocket.tool.BitmapUtil.load
import cn.imgrocket.imgrocket.tool.Function.toast
import cn.imgrocket.imgrocket.tool.Storage
import cn.imgrocket.imgrocket.tool.URL
import com.yalantis.ucrop.UCrop
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
import java.util.*


class UploadAvatarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadAvatarBinding
    private val global: APP by lazy {
        application as APP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        global = application as APP
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
            val file = Storage.getStorageDirectory(context, "cache")
            val pic = File(file, BitmapUtil.random(Date()))
            pic.createNewFile()
            val destinationUri = Uri.fromFile(pic)
            UCrop.of(result[0], destinationUri)
                    .withAspectRatio(1F, 1F)
                    .withMaxResultSize(512, 512)
                    .start(this@UploadAvatarActivity);
        } else {
            finish()
        }
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri: Uri? = UCrop.getOutput(data!!)
            val avatar = load(this, resultUri!!)
            post(URL.uploadAvatarURL, global.user?.uid!!, global.user?.token!!, bitmap2FileCache(context, avatar!!, 85), object : Callback {
                override fun onResponse(result: String?) {
                    toast(result!!)
                    global.avatarVersion++
                    //TODO 调用UploadAvatarActivity的refreshAvatar方法，刷新头像
                    finish()

                }
            })
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)?.apply { printStackTrace() }
            Log.e("crop error", cropError.toString())
            finish()
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
        Thread {
            val result = post(url, uid, token, portrait)
            handler.post { callback.onResponse(result) }
        }.start()
    }


    internal interface Callback {
        fun onResponse(result: String?)
    }


    companion object {
        private const val REQUEST_CODE_CHOOSE = 111
    }
}
