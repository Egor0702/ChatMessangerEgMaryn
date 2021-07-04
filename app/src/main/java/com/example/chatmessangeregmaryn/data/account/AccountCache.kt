package com.example.chatmessangeregmaryn.data.account

import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.exception.Failure

interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>
}