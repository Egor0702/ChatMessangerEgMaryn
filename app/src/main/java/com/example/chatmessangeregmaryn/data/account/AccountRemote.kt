package com.example.chatmessangeregmaryn.data.account

import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.exception.Failure

interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userDate: Long
    ): Either<Failure, None>
}