package com.example.chatmessangeregmaryn.data.friends

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
    override fun getFriends(): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount() // здесь мы запрашиваем все данные текущего (активного) пользователя. При успехе нам возвращается Right  с данными
                .flatMap { friendsRemote.getFriends(it.id, it.token) } // вполняеь метод  getFriends с переданным в него обэектом FriendsEntity
    }

    override fun getFriendRequests(): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount() // запрашиваем данные пользователя
                .flatMap { friendsRemote.getFriendRequests(it.id, it.token) } // возвращаем список приглашений
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.addFriend(email, it.id, it.token) }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.deleteFriend(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }
}