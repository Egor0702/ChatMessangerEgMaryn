package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class DeleteFriend @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() { // for remove friend
    init {
        Log.d("Egor", "Всем хло, мы в DeleteFriendRequest")
    }
    override suspend fun run(params: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "DeleteFriend run()")
        return friendsRepository.deleteFriend(params)
    }
}