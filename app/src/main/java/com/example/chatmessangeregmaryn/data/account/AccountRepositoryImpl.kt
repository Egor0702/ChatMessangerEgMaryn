package com.example.chatmessangeregmaryn.data.account

import android.util.Log
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.account.AccountRepository
import com.example.chatmessangeregmaryn.domain.type.*
import java.util.*
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemote: AccountRemote,
    private val accountCache: AccountCache
) : AccountRepository {
    init {
        Log.d("Egor", "Всем хло, мы в AccountRepositoryImpl")
    }

    override fun login(email: String, password: String): Either<Failure, AccountEntity> {
        Log.d("Egor", "AccountRepositoryImpl login()")
        return accountCache.getToken().flatMap {
            accountRemote.login(email, password, it)
        }.onNext {
            it.password = password // сохраняем пароль, так как сервер его не возвращает
            accountCache.saveAccount(it)
        }
    }

    override fun logout(): Either<Failure, None> {
        Log.d("Egor", "AccountRepositoryImpl logout()")
        return accountCache.logout()
    }

    override fun register(email: String, name: String, password: String): Either<Failure, None> {
        Log.d("Egor", "AccountRepositoryImpl register()")
        return accountCache.getToken().flatMap {
            accountRemote.register(email, name, password, it, Calendar.getInstance().timeInMillis)
        }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        Log.d("Egor", "AccountRepositoryImpl forgetPassword()")
        throw UnsupportedOperationException("Password recovery is not supported")
    }


    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        Log.d("Egor", "AccountRepositoryImpl getCurrentAccount()")
        return accountCache.getCurrentAccount()
    }


    override fun updateAccountToken(token: String): Either<Failure, None> {
        Log.d("Egor", "AccountRepositoryImpl updateAccountToken()")
        accountCache.saveToken(token)

        return accountCache.getCurrentAccount()
            .flatMap { accountRemote.updateToken(it.id, token, it.token) }
    }

    override fun updateAccountLastSeen(): Either<Failure, None> {
        Log.d("Egor", "AccountRepositoryImpl updateAccountLastSeen()")
        throw UnsupportedOperationException("Updating last seen is not supported")
    }


    override fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity> { // меняем данные пользователя на сервере и в БД
        Log.d("Egor", "AccountRepositoryImpl editAccount()")
        return accountCache.getCurrentAccount().flatMap {
            accountRemote.editUser(entity.id, entity.email, entity.name, entity.password,
                entity.status, it.token, entity.image)
        }.onNext {
            entity.image = it.image // полученные из сервера изображения сохраняем в данных о пользователе
            accountCache.saveAccount(entity)
        }
    }
}