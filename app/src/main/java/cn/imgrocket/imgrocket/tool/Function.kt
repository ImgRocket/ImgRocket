package cn.imgrocket.imgrocket.tool

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import cn.imgrocket.imgrocket.tool.APP.Companion.context
import cn.imgrocket.imgrocket.tool.Function.Callback
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.String


//Function类存储了通用的方法

internal object Function {
    fun post(url: String, obj: Any): String? {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val okHttpClient = OkHttpClient()
        val requestBody = Gson().toJson(obj).toRequestBody(JSON)
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
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

    fun post(url: String, s: String): String? {
        val string = "text/text; charset=utf-8".toMediaTypeOrNull()
        val okHttpClient = OkHttpClient()
        val requestBody = s.toRequestBody(string)
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
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

    fun post(url: String, obj: Any, callback: Callback) {
        val handler = Handler()
        Thread(Runnable {
            val result = post(url, obj)
            handler.post { callback.onResponse(result) }
        }).start()
    }

    fun post(url: String, s: String, callback: Callback) {
        val handler = Handler()
        Thread(Runnable {
            val result = post(url, s)
            handler.post { callback.onResponse(result) }
        }).start()
    }

    internal interface Callback {
        fun onResponse(result: String?)
    }

    fun black(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
    }

    fun toast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun testActivity(activityFrom: Activity, activityTo: Activity) {
        val intent = Intent()
        intent.setClass(activityFrom.applicationContext, activityTo::class.java)
        activityFrom.startActivity(intent)
    }

    fun getSalt(): String {
        return "wcfnb"
    }

}



