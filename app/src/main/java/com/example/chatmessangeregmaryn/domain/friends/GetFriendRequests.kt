package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, Boolean>() {
    init {
        Log.d("Egor", "Всем хло, мы в GetFriendRequests")
    }

    override suspend fun run(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "GetFriendRequests run()")
        return friendsRepository.getFriendRequests(needFetch) // аргумент указыаает загружаем инфу из БД или с сервера
    }
}