package com.example.chatmessangeregmaryn.domain.friends

import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class DeleteFriend @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() { // for remove friend

    override suspend fun run(params: FriendEntity) = friendsRepository.deleteFriend(params)
}