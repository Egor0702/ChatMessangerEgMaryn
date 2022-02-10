package com.example.chatmessangeregmaryn.data.account

import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.Failure

interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>
    fun login(email: String, password: String, token: String): Either<Failure, AccountEntity>
    fun updateToken(userId: Long, token: String, oldToken: String): Either<Failure, None>
    fun editUser(
        userId: Long,
        email: String,
        name: String,
        password: String,
        status: String,
        token: String,
        image: String
    ): Either<Failure, AccountEntity> //  меняет данные пользоватаеля на сервере.
}