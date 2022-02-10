package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class Logout @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, None>() {
    init {
        Log.d("Egor", "Всем хло, мы в Logout")
    }

    override suspend fun run(params: None): Either<Failure, None> {
        Log.d("Egor", "Logout run()")
        return accountRepository.logout()
    }
}