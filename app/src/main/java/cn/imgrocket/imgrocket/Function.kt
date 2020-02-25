package cn.imgrocket.imgrocket

import android.os.Handler

import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig

import java.io.IOException

import okhttp3.*


//Function类存储了通用的方法

internal object Function {
    fun post(url: String, obj: Any): String? {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        val okHttpClient = OkHttpClient()
        val requestBody = RequestBody.create(JSON, Gson().toJson(obj))
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body()!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun post(url: String, s: String): String? {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        val okHttpClient = OkHttpClient()
        val requestBody = RequestBody.create(JSON, s)
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body()!!.string()
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
}
