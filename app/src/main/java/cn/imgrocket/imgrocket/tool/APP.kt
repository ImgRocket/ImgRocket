package cn.imgrocket.imgrocket.tool

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import cn.imgrocket.imgrocket.room.AppDatabase
import cn.imgrocket.imgrocket.room.model.User
import cn.imgrocket.imgrocket.room.model.UserDao
import kotlin.properties.Delegates

class APP : Application() {
    lateinit var userData: LiveData<User?>
    var user: User? by Delegates.vetoable<User?>(null) { _, _, user ->
        for (listener in userStateListeners) {
            listener.onUserChange(user)
        }
        return@vetoable true
    }

    var avatarVersion by Delegates.observable(0) { _, _, version ->
        for (listener in avatarListeners) {
            listener.onAvatarChange(version)
        }
    }

    private val avatarListeners = HashSet<AvatarListener>()
    fun addAvatarChangeListener(listener: AvatarListener) {
        avatarListeners.add(listener)
    }
    fun removeAvatarChangeListener(listener: AvatarListener) {
        avatarListeners.remove(listener)
    }

    private val userStateListeners = HashSet<UserStateChangeListener>()
    fun addOnUserStateChangeListener(listener: UserStateChangeListener) {
        userStateListeners.add(listener)
    }
    fun removeOnUserStateChangeListener(listener: UserStateChangeListener) {
        userStateListeners.remove(listener)
    }


    lateinit var database: AppDatabase
    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onTerminate() {
        super.onTerminate()
        avatarListeners.clear()
        userStateListeners.clear()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}

interface AvatarListener {
    fun onAvatarChange(avatarVersion: Int)
}

interface UserStateChangeListener {
    fun onUserChange(user: User?)
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