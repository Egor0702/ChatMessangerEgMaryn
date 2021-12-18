package com.example.chatmessangeregmaryn.domain.friends

import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class CancelFriendRequest @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<None, FriendEntity>() { // class for rejecting a friendship invitation

    override suspend fun run(params: FriendEntity) = friendsRepository.cancelFriendRequest(params)
}