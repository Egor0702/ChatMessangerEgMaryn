package com.example.chatmessangeregmaryn.cache

import android.util.Log
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager) : AccountCache {
    init {
        Log.d("Egor", "Всем хло, мы в AccountCacheImpl")
    }
    override fun saveToken(token: String): Either<Failure, None> {
        Log.d("Egor", "AccountCacheImpl saveToken()")
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        Log.d("Egor", "AccountCacheImpl getToken()")
        return prefsManager.getToken()
    }

    override fun logout(): Either<Failure, None> {
        Log.d("Egor", "AccountCacheImpl logout()")
        return prefsManager.removeAccount()
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        Log.d("Egor", "AccountCacheImpl getCurrentAccount()")
        return prefsManager.getAccount()
    }

    override fun saveAccount(account: AccountEntity): Either<Failure, AccountEntity> {
        Log.d("Egor", "AccountCacheImpl saveAccount()")
        return prefsManager.saveAccount(account)
    }
}