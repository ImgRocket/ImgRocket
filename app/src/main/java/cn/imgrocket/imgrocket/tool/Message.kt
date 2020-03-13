package cn.imgrocket.imgrocket.tool

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

enum class Status {
    AE, // argument mismatch
    UR, // nickname have been registered
    OK, // success
    UNE, // user not found
    UPE, // password error
    TE, // invalid token
    AIF, // argument format incorrect
    PME, // permission not allowed
    OTHER;
}

class Message<T>(val status: Status, val message: String, val data: T? = null) {
    companion object {
        fun <T> parse(json: String, type: TypeToken<T>): T {
            return gson.fromJson(json, type.type)
        }
        private val gson = Gson()
    }
}

class LoginResult(var uid: String, var username: String, var token: String)
