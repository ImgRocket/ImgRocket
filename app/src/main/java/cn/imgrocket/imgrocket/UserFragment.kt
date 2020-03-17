package cn.imgrocket.imgrocket

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import cn.imgrocket.imgrocket.databinding.FragmentUserBinding
import cn.imgrocket.imgrocket.tool.*
import cn.imgrocket.imgrocket.tool.Function
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var global: APP

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.name, "UserFragment OnResume ${global.user}")
        refreshAvatar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            if (result.isEmpty()) return
            val file = Storage.getStorageDirectory(APP.context, "cache")
            val pic = File(file, BitmapUtil.random(Date()))
            pic.createNewFile()
            val destinationUri = Uri.fromFile(pic)

            context?.let { it1 ->
                UCrop.of(result[0], destinationUri)
                        .withAspectRatio(1F, 1F)
                        .withMaxResultSize(512, 512)
                        .start(it1, this)

            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri: Uri? = UCrop.getOutput(data!!)
            val avatar = activity?.let { BitmapUtil.load(it, resultUri!!) }
            post(URL.uploadAvatarURL, global.user?.uid!!, global.user?.token!!, BitmapUtil.bitmap2FileCache(APP.context, avatar!!, 85), object : Callback {
                override fun onResponse(result: String?) {
                    Function.toast(result!!)
                    global.avatarVersion++
                    refreshAvatar()
                }
            })
        } else if (resultCode == UCrop.RESULT_ERROR) {
            data?.let { UCrop.getError(it) }?.apply { printStackTrace() }
            Function.toast("error")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        global = activity?.application as APP

        binding.userImageUser.setOnClickListener {
            val intent = Intent()
            if (global.user != null) {
//                activity?.let { it1 -> intent.setClass(it1, UploadAvatarActivity::class.java) }
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .theme(R.style.Matisse_Custom)
                        .maxSelectable(1)
                        .capture(false)
                        .imageEngine(PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE)
            } else {
                activity?.let { it1 -> intent.setClass(it1, LoginActivity::class.java) }
                startActivity(intent)
            }
//            startActivity(intent)
        }
        binding.userLayoutItem1.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem2.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem3.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem4.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userLayoutItem5.setOnClickListener {
            Function.toast(resources.getString(R.string.this_function_is_unavailable))
        }
        binding.userSeekRound.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val density = context?.resources?.displayMetrics?.density
                binding.userImageUser.setRound((progress * density!! + 0.5).toInt())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun refreshAvatar() {
        global.user?.apply {
            binding.userTextUsername.text = username
            binding.userTextUserNumber.text = uid
            Glide.with(this@UserFragment)
                    .load(URL.avatarURL + uid + "&version=" + global.avatarVersion)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.userImageUser)
            binding.userImageUser.setColorFilter(Color.TRANSPARENT)
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


