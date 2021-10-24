package com.example.chatmessangeregmaryn.data.account

import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Failure

interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>
    fun logout(): Either<Failure, None>
    fun getCurrentAccount(): Either<Failure, AccountEntity>
    fun saveAccount(account: AccountEntity): Either<Failure, None>
}