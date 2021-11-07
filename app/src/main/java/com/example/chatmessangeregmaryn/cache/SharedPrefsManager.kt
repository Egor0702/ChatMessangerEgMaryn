package com.example.chatmessangeregmaryn.cache

import android.content.SharedPreferences
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val prefs: SharedPreferences) {
    companion object {
        const val ACCOUNT_TOKEN = "account_token"
        const val ACCOUNT_ID = "account_id"
        const val ACCOUNT_NAME = "account_name"
        const val ACCOUNT_EMAIL = "account_email"
        const val ACCOUNT_STATUS = "account_status"
        const val ACCOUNT_DATE = "account_date"
        const val ACCOUNT_IMAGE = "account_image"
        const val ACCOUNT_LASTSEEN = "account_lastseen"
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
    fun saveAccount(account: AccountEntity): Either<Failure, None> {
        prefs.edit().apply {
            putLong(ACCOUNT_ID, account.id)
            putString(ACCOUNT_NAME, account.name)
            putString(ACCOUNT_EMAIL, account.email)
            putString(ACCOUNT_TOKEN, account.token)
            putString(ACCOUNT_STATUS, account.status)
            putLong(ACCOUNT_DATE, account.userDate)
            putLong(ACCOUNT_IMAGE, account.image)
            putLong(ACCOUNT_LASTSEEN, account.lastSeen)
        }.apply()

        return Either.Right(None())
    }
    fun getAccount(): Either<Failure, AccountEntity> {
        val id = prefs.getLong(ACCOUNT_ID, 0)
        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }
        val account = AccountEntity(
            prefs.getLong(ACCOUNT_ID, 0),
            prefs.getString(ACCOUNT_NAME, "")!!,
            prefs.getString(ACCOUNT_EMAIL, "")!!,
            prefs.getString(ACCOUNT_TOKEN, "")!!,
            prefs.getString(ACCOUNT_STATUS, "")!!,
            prefs.getLong(ACCOUNT_DATE, 0),
            prefs.getLong(ACCOUNT_IMAGE, 0)!!,
        prefs.getLong(ACCOUNT_LASTSEEN,0)
        )
        return Either.Right(account)
    }

    fun removeAccount(): Either<Failure, None> {
        prefs.edit().apply {
            remove(ACCOUNT_ID)
            remove(ACCOUNT_NAME)
            remove(ACCOUNT_EMAIL)
            remove(ACCOUNT_STATUS)
            remove(ACCOUNT_DATE)
            remove(ACCOUNT_IMAGE)
        }.apply()

        return Either.Right(None())
    }

    fun containsAnyAccount(): Boolean {
        val id = prefs.getLong(ACCOUNT_ID, 0)
        return id != 0L
    }
}