package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class CancelFriendRequest @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() { // class for rejecting a friendship invitation
    init {
        Log.d("Egor", "Всем хло, мы в CancelFriendRequest")
    }
    override suspend fun run(params: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "CancelFriendRequest run()")
        return friendsRepository.cancelFriendRequest(params)
    }
}