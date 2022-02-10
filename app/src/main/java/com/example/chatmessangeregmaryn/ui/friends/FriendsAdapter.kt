package com.example.chatmessangeregmaryn.ui.friends

import android.util.Log
import android.view.View
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.ui.core.BaseAdapter
import kotlinx.android.synthetic.main.item_friend.view.*

open class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {
    init {
        Log.d("Egor", "Всем хло, мы в FriendsAdapter")
    }
    override val layoutRes = R.layout.item_friend

    override fun createHolder(view: View, viewType: Int): FriendViewHolder {
        Log.d("Egor", "FriendsAdapter createHolder()")
        return FriendViewHolder(view)
    }

    class FriendViewHolder(view: View) : BaseViewHolder(view) {

        init {
            Log.d("Egor", "FriendViewHolder init")
            view.btnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                view.tvName.text = it.name
                view.tvStatus.text = it.status
            }

        }
    }
}