package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetAccount @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<AccountEntity, None>() {
    init {
        Log.d("Egor", "Всем хло, мы в GetAccount")
    }
    override suspend fun run(params: None): Either<Failure, AccountEntity> {
        Log.d("Egor", "GetAccount run()")
        return accountRepository.getCurrentAccount()
    }
}