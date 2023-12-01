package com.rocketreserver.http

import com.app.storage.settings


object TokenRepository {
    private const val KEY_TOKEN = "TOKEN"

    fun getToken(): String? {
        val token = settings.getString(KEY_TOKEN, "")
        if(token != "") {
            return token
        }
        return null
    }

    fun setToken(token: String) {
        settings.putString(KEY_TOKEN, token)
    }

    fun removeToken() {
        settings.remove(KEY_TOKEN)
    }
}
