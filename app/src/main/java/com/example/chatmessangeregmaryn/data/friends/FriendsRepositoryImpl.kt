package com.example.chatmessangeregmaryn.data.friends

import android.util.Log
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.friends.FriendsRepository
import com.example.chatmessangeregmaryn.domain.type.Either
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.domain.type.flatMap

class FriendsRepositoryImpl (
        private val accountCache: AccountCache,
        private val friendsRemote: FriendsRemote
        ) : FriendsRepository{
    init {
        Log.d("Egor", "Всем хло, мы в FriendsRepositoryImpl")
    }
    override fun getFriends(): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "FriendsRepositoryImpl getFriends()")
        return accountCache.getCurrentAccount() // здесь мы запрашиваем все данные текущего (активного) пользователя. При успехе нам возвращается Right  с данными
                .flatMap { friendsRemote.getFriends(it.id, it.token) } // вполняеь метод  getFriends с переданным в него обэектом FriendsEntity
    }

    override fun getFriendRequests(): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "FriendsRepositoryImpl getFriendRequests()")
        return accountCache.getCurrentAccount() // запрашиваем данные пользователя
                .flatMap { friendsRemote.getFriendRequests(it.id, it.token) } // возвращаем список приглашений
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "FriendsRepositoryImpl approveFriendRequest()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "FriendsRepositoryImpl cancelFriendRequest()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        Log.d("Egor", "FriendsRepositoryImpl addFriend()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.addFriend(email, it.id, it.token) }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "FriendsRepositoryImpl deleteFriend()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.deleteFriend(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }
}