package com.example.chatmessangeregmaryn.domain.friends

import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, None>() {

    override suspend fun run(params: None) = friendsRepository.getFriendRequests()
}