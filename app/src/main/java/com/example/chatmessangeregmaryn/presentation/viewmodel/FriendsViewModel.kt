package com.example.chatmessangeregmaryn.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatmessangeregmaryn.domain.friends.*
import com.example.chatmessangeregmaryn.domain.type.None
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
        val getFriendsUseCase: GetFriends,
        val deleteFriendUseCase: DeleteFriend,
        val addFriendUseCase: AddFriend,
        val getFriendRequestsUseCase: GetFriendRequests,
        val approveFriendRequestUseCase: ApproveFriendRequest,
        val cancelFriendRequestUseCase: CancelFriendRequest
) : BaseViewModel() {
    init {
        Log.d("Egor", "Всем хло, мы в FriendsViewModel")
    }
    var friendsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    var friendRequestsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    var deleteFriendData: MutableLiveData<None> = MutableLiveData()
    var addFriendData: MutableLiveData<None> = MutableLiveData()
    var approveFriendData: MutableLiveData<None> = MutableLiveData()
    var cancelFriendData: MutableLiveData<None> = MutableLiveData()

    fun getFriends() {
        Log.d("Egor", "FriendsViewModel getFriends()")
        getFriendsUseCase(None()) { it.either(::handleFailure, ::handleFriends) }
    }

    fun getFriendRequests() {
        Log.d("Egor", "FriendsViewModel getFriendRequests()")
        getFriendRequestsUseCase(None()) { it.either(::handleFailure, ::handleFriendRequests) }
    }

    fun deleteFriend(friendEntity: FriendEntity) {
        Log.d("Egor", "FriendsViewModel deleteFriend()")
        deleteFriendUseCase(friendEntity) { it.either(::handleFailure, ::handleDeleteFriend) }
    }

    fun addFriend(email: String) { // вызывается из HomeActivity, когда пользователь ввел эмейл и нажал кнопку "Отправить запрос"
        Log.d("Egor", "FriendsViewModel addFriend()")
        addFriendUseCase(AddFriend.Params(email)) { it.either(::handleFailure, ::handleAddFriend) }
    }

    fun approveFriend(friendEntity: FriendEntity) {
        Log.d("Egor", "FriendsViewModel approveFriend()")
        approveFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleApproveFriend) }
    }

    fun cancelFriend(friendEntity: FriendEntity) {
        Log.d("Egor", "FriendsViewModel cancelFriend()")
        cancelFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleCancelFriend) }
    }


    private fun handleFriends(friends: List<FriendEntity>) {
        Log.d("Egor", "FriendsViewModel handleFriend()")
        friendsData.value = friends
    }

    private fun handleFriendRequests(friends: List<FriendEntity>) {
        Log.d("Egor", "FriendsViewModel handleFriendRequests()")
        friendRequestsData.value = friends
    }

    private fun handleDeleteFriend(none: None?) {
        Log.d("Egor", "FriendsViewModel handleDeleteFriend()")
        deleteFriendData.value = none
    }

    private fun handleAddFriend(none: None?) {
        Log.d("Egor", "FriendsViewModel handleAddFriend()")
        addFriendData.value = none
    }

    private fun handleApproveFriend(none: None?) {
        Log.d("Egor", "FriendsViewModel handleApproveFriend()")
        approveFriendData.value = none
    }

    private fun handleCancelFriend(none: None?) {
        Log.d("Egor", "FriendsViewModel handleCancelFriend()")
        cancelFriendData.value = none
    }


    override fun onCleared() {
        Log.d("Egor", "FriendsViewModel onCleared()")
        super.onCleared()
        getFriendsUseCase.unsubscribe()
        getFriendRequestsUseCase.unsubscribe()
        deleteFriendUseCase.unsubscribe()
        addFriendUseCase.unsubscribe()
        approveFriendRequestUseCase.unsubscribe()
        cancelFriendRequestUseCase.unsubscribe()
    }
}