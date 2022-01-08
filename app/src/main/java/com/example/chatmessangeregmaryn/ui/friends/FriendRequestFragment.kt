package com.example.chatmessangeregmaryn.ui.friends

import android.os.Bundle
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
    override val viewAdapter = FriendRequestsAdapter()

    override val layoutId = R.layout.inner_fragment_list

    lateinit var friendsViewModel: FriendsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base {
            friendsViewModel = viewModel {
                onSuccess(friendRequestsData, ::handleFriendRequests)
                onSuccess(approveFriendData, ::handleFriendRequestAction)
                onSuccess(cancelFriendData, ::handleFriendRequestAction)
                onFailure(failureData, ::handleFailure)
            }
        }
        setOnItemClickListener { item, v ->
            (item as? FriendEntity)?.let {
                when (v.id) {
                    R.id.btnApprove -> {
                        showProgress()
                        friendsViewModel.approveFriend(it)
                    }
                    R.id.btnCancel -> {
                        showProgress()
                        friendsViewModel.cancelFriend(it)
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        showProgress()
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        hideProgress()
        if (requests != null) {
            viewAdapter.clear()
            viewAdapter.add(requests)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}
