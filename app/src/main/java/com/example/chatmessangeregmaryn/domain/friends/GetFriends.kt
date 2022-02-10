package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class GetFriends @Inject constructor(
        private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, None>() { // for getting list of friends
    init {
        Log.d("Egor", "Всем хло, мы в GetFriends")
    }
    override suspend fun run(params: None): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "GetFriends run()")
       return friendsRepository.getFriends()
    }
}