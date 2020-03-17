package cn.imgrocket.imgrocket.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.imgrocket.imgrocket.room.model.User
import cn.imgrocket.imgrocket.room.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}