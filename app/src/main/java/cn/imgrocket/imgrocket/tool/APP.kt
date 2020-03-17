package cn.imgrocket.imgrocket.tool

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import cn.imgrocket.imgrocket.room.AppDatabase
import cn.imgrocket.imgrocket.room.model.User
import cn.imgrocket.imgrocket.room.model.UserDao

class APP : Application() {
//    lateinit var userData: LiveData<Array<User>>
    var user: User? = null
    var avatarVersion: Int = 0

    lateinit var database: AppDatabase
    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
}