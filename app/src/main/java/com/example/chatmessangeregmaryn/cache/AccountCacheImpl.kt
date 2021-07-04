package com.example.chatmessangeregmaryn.cache

import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.exception.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager) : AccountCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }
}