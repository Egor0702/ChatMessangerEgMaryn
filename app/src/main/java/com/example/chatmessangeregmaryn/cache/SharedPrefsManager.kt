package com.example.chatmessangeregmaryn.cache

import android.content.SharedPreferences
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.exception.Failure
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val prefs: SharedPreferences) {
    companion object {
        const val ACCOUNT_TOKEN = "account_token"
    }

    fun saveToken(token: String): Either<Failure, None> {
        prefs.edit().apply {
            putString(ACCOUNT_TOKEN, token)
        }.apply() // SharedPreference.apply() позволяет отобразить наши изменения в оригинальном SharedPreference

        return Either.Right(None())
    }

    fun getToken(): Either<Failure, String> {
        val str = prefs.getString(ACCOUNT_TOKEN, "")
        return Either.Right(str?:"")
    }
}