package cn.imgrocket.imgrocket.room.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(
    tableName = "user",
    indices = [
        Index(value = ["uid"], unique = true),
        Index(value = ["username"], unique = true)
    ]
)
class User(
    @PrimaryKey val uid: String,
    val username: String,
    val token: String,
    val enable: Int
) {
    override fun toString(): String {
        return "$uid, $username, $token, $enable"
    }
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg users: User): Int

    @Query("update user set enable = -1")
    fun invalidateAll()

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM user where enable >= 0 LIMIT 1")
    fun loadUser(): User?

    @Query("SELECT * FROM user where enable >= 0 LIMIT 1")
    fun autoUpdateLoadUser(): LiveData<Array<User>>
}
