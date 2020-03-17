package cn.imgrocket.imgrocket.tool

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import cn.imgrocket.imgrocket.MainActivity
import cn.imgrocket.imgrocket.room.AppDatabase
import cn.imgrocket.imgrocket.room.model.User
import cn.imgrocket.imgrocket.room.model.UserDao
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class APP : Application() {
    lateinit var userData: LiveData<User?>
    var user: User? = null
    var avatarVersion by Delegates.observable(0) { _, _: Int, new: Int ->
        for (listener in listeners) {
            listener.onAvatarChange(new)
        }
    }

    private val listeners = HashSet<AvatarListener>()
    fun addAvatarChangeListener(listener: AvatarListener) {
        listeners.add(listener)
    }

    lateinit var database: AppDatabase
    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onTerminate() {
        super.onTerminate()
        listeners.clear()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}

interface AvatarListener {
    fun onAvatarChange(avatarVersion: Int)
}

/*
委托
*/
//class Avatar {
//    var avatarVersion = 0
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
//        return avatarVersion
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
//        avatarVersion = value
//    }
//}