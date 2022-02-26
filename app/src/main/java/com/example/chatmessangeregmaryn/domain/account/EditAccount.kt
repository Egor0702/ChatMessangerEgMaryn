package com.example.chatmessangeregmaryn.domain.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import javax.inject.Inject

class EditAccount @Inject constructor( // класс для изменения данных текущего пользователя
    private val repository: AccountRepository
) : UseCase<AccountEntity, AccountEntity>() {
    init {
        Log.d("Egor", "Всем хло, мы в EditAccount")
    }
    override suspend fun run(params: AccountEntity): Either<Failure, AccountEntity> {
        Log.d("Egor", "EditAccount run()")
        return repository.editAccount(params)
    }
}