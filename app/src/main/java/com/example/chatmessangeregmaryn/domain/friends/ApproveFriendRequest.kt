package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class ApproveFriendRequest @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase <None, FriendEntity>(){ // class for accept invitiations friend
    init {
        Log.d("Egor", "Всем хло, мы в ApproveFriendRequest")
    }
    override suspend fun run(params: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "ApproveFriendRequest run()")
        return friendsRepository.approveFriendRequest(params)
    }
}