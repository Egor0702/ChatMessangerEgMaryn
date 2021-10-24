package com.example.chatmessangeregmaryn.remote.account

import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.remote.core.BaseResponse

class AuthResponse( // POJO class
    success: Int,
    message: String,
    val user: AccountEntity
    ) : BaseResponse(success, message)
