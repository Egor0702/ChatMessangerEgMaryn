package com.example.chatmessangeregmaryn.ui.friends

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.presentation.viewmodel.FriendsViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.core.BaseListFragment
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess

class FriendRequestsFragment : BaseListFragment() {
    init {
        Log.d("Egor", "Всем хло, мы в FriendRequestsFragment")
    }
    override val viewAdapter = FriendRequestsAdapter()

    override val layoutId = R.layout.inner_fragment_list

    lateinit var friendsViewModel: FriendsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "FriendRequestsFragment onCreate()")
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Egor", "FriendRequestsFragment onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        base {
            friendsViewModel = viewModel {
                onSuccess(friendRequestsData, ::handleFriendRequests)
                onSuccess(approveFriendData, ::handleFriendRequestAction)
                onSuccess(cancelFriendData, ::handleFriendRequestAction)
                onFailure(failureData, ::handleFailure)
            }
        }
        setOnItemClickListener { item, v -> // обрабатываем клик пользователя на списке с приглашениями
            Log.d("Egor", "FriendRequestsFragment  setOnItemClickListener")
            (item as? FriendEntity)?.let {
                when (v.id) {
                    R.id.btnApprove -> { // если пользователь нажимает галочку, т.е. принмает приглашение в друзья:
                        showProgress() // отображаем прогресс бар
                        friendsViewModel.approveFriend(it) // запускаем механизм принятия пользователя в друзья
                    }
                    R.id.btnCancel -> { // если пользователь нажимает крестик, т.е. отказывается принять в друзья пользовтеля, то:
                        showProgress() // отображаем прошресс бао
                        friendsViewModel.cancelFriend(it) // запускаем ссответсвующий метод
                    }
                    else -> { // если клик на пользователя, который добавляется, то направляем его в профиль друга
                    activity?.let { activity -> // activity ищзвращает activity, который связан с данным fragment
                        navigator.showUser(activity, it) // передаем в метод шоуюзер контекст и объект FriendEntity
                    }
                }
                }
            }
        }
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) { // в случае, если список на который мы подписаны обновлен, выполняются действия:
        Log.d("Egor", "FriendRequestsFragment handleFriendRequests()")
        hideProgress() // закрываем прогресс бар
        requests?.let { // если requests не null
            viewAdapter.clear() // очищаем имеющийся список в recyclerview
            viewAdapter.add(requests) // добавляем новый список
            viewAdapter.notifyDataSetChanged() // бновляем данные, чтобы они отобразились на экране пользователя
        }
    }

    private fun handleFriendRequestAction(none: None?) { // повторное получение списка. Вызывается, когда пользователь принял или отклонил приглашение
        Log.d("Egor", "FriendRequestsFragment handleFriendRequestAction()")
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}
