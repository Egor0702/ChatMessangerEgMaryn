package com.example.chatmessangeregmaryn.domain.account

import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import javax.inject.Inject

class UpdateToken @Inject constructor(private val accountRepository: AccountRepository) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params) = accountRepository.updateAccountToken(params.token)

    data class Params(val token: String)
}