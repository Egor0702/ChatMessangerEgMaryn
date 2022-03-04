package com.example.chatmessangeregmaryn.data.friends

import android.util.Log
import com.example.chatmessangeregmaryn.data.account.AccountCache
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.friends.FriendsRepository
import com.example.chatmessangeregmaryn.domain.type.*

class FriendsRepositoryImpl (
        private val accountCache: AccountCache,
        private val friendsRemote: FriendsRemote,
        private val friendsCache: FriendsCache
        ) : FriendsRepository{
    init {
        Log.d("Egor", "Всем хло, мы в FriendsRepositoryImpl")
    }
    override fun getFriends(needFetch : Boolean): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "FriendsRepositoryImpl getFriends()")
        return accountCache.getCurrentAccount() // здесь мы запрашиваем все данные текущего (активного) пользователя. При успехе нам возвращается Right  с данными
                .flatMap {
                    return@flatMap if (needFetch){
                        friendsRemote.getFriends(it.id, it.token) // вполняеь метод  getFriends с переданным в него обэектом FriendsEntity c сервера
                    }else{
                        Either.Right(friendsCache.getFriends()) // выполняем запрос из БД
                    }
                }
                .onNext { it.map { friendsCache.saveFriend(it) } } //  map Возвращает список, содержащий результаты применения заданной функции преобразования к каждому элементу исходного массива. То есть мы ничего не изменили в списке, но смогли каждый из элементов сохранить в БД
    }

    override fun getFriendRequests(needFetch : Boolean): Either<Failure, List<FriendEntity>> {
        Log.d("Egor", "FriendsRepositoryImpl getFriendRequests()")
        return accountCache.getCurrentAccount() // запрашиваем данные пользователя
                .flatMap {
                    return@flatMap if (needFetch) {
                        friendsRemote.getFriendRequests(it.id, it.token) // возвращаем список приглашений c сервера
                    } else{
                        Either.Right(friendsCache.getFriendRequests()) // из БД
                    }
                    }
                .onNext { it.map {
                    it.isRequests = 1 // является приглашением в друзья
                    friendsCache.saveFriend(it) }  }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> { // метод, выполянющий действия, когда пользователь согласился принять приглашение в друзья
        Log.d("Egor", "FriendsRepositoryImpl approveFriendRequest()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
                .onNext {
                    friendEntity.isRequests = 0 // пользователь является другом
                    friendsCache.saveFriend(friendEntity)
                }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        Log.d("Egor", "FriendsRepositoryImpl cancelFriendRequest()")
        return accountCache.getCurrentAccount()
                .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
                .onNext { friendsCache.removeFriendEntity(friendEntity.id) } // удаляем приглашение в друзья из бд
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
                .onNext { friendsCache.removeFriendEntity(friendEntity.id) }
    }
}