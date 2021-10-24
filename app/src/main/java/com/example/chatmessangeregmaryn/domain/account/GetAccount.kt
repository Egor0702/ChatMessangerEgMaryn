package com.example.chatmessangeregmaryn.domain.account

import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetAccount @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, None>() {

    override suspend fun run(params: None) = accountRepository.getCurrentAccount()
}