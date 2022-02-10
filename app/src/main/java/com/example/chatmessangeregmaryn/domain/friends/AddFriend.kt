package com.example.chatmessangeregmaryn.domain.friends

import android.util.Log
import com.example.chatmessangeregmaryn.domain.interactor.UseCase
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class AddFriend @Inject constructor(
        private  val friendsRepository: FriendsRepository
        ) : UseCase<None, AddFriend.Params>() { // class for send to invitation friend
    init {
        Log.d("Egor", "Всем хло, мы в AddFriend")
    }

    override suspend fun run(params: AddFriend.Params): Either<Failure, None> {
        Log.d("Egor", "AddFriend run()")
        return friendsRepository.addFriend(params.email)
    }

    data class Params(val email: String)
}