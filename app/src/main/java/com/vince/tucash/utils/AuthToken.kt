package com.vince.tucash.utils

import android.content.Context
import android.content.SharedPreferences

class AuthToken private constructor(context: Context) {
    companion object {

        private const val AUTH_PREFS = "AUTH_PREFS"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val PHONE_NUMBER = "PHONE_NUMBER"
        private const val USER_ID = "USER_ID"

        @Volatile
        private var instance: AuthToken? = null

        fun getInstance(context: Context): AuthToken = instance ?: synchronized(this) {
            AuthToken(context).apply { instance = this }
        }
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
//
//    var userID: Int?
//        set(value) = sharedPreferences.edit().putInt(USER_ID, value ?: 0).apply()
//        get() = sharedPreferences.getInt(USER_ID, 0)
//
//    var accessToken: String?
//        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()
//        get() = sharedPreferences.getString(ACCESS_TOKEN, null)
//
//    var refreshToken: String?
//        set(value) = sharedPreferences.edit().putString(REFRESH_TOKEN, value).apply()
//        get() = sharedPreferences.getString(REFRESH_TOKEN, null)
//
//    var phoneNumber: String?
//        set(value) = sharedPreferences.edit().putString(PHONE_NUMBER, value).apply()
//        get() = sharedPreferences.getString(PHONE_NUMBER, null)
}
