package com.example.chatmessangeregmaryn.domain.friends

import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetFriends @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, None>() { // for getting list of friends

    override suspend fun run(params: None) = friendsRepository.getFriends()
}