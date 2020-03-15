package cn.imgrocket.imgrocket.tool

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class APP : Application() {
    var token: String? = null
    var nickname: String? = null
    var username: String? = null
    var uid: String? = null
    var login: Boolean = false
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
}