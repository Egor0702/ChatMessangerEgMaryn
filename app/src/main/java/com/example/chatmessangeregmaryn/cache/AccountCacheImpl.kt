package com.example.chatmessangeregmaryn.cache

import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager) : AccountCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }

    override fun logout(): Either<Failure, None> {
        return prefsManager.removeAccount()
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        return prefsManager.getAccount()
    }

    override fun saveAccount(account: AccountEntity): Either<Failure, None> {
        return prefsManager.saveAccount(account)
    }
}