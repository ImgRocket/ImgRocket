package cn.imgrocket.imgrocket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
}