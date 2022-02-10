package com.example.chatmessangeregmaryn.ui.friends

import android.util.Log
import android.view.View
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.ui.core.BaseAdapter
import com.example.chatmessangeregmaryn.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend_request.view.*

open class FriendRequestsAdapter : BaseAdapter<FriendRequestsAdapter.FriendRequestViewHolder>() {
    init {
        Log.d("Egor", "Всем хло, мы в FriendRequestAdapter")
    }
    override val layoutRes = R.layout.item_friend_request

    override fun createHolder(view: View, viewType: Int): FriendRequestViewHolder {
        Log.d("Egor", "FriendRequestsAdapter createHolder()")
        return FriendRequestViewHolder(view)
    }

    class FriendRequestViewHolder(view: View) : BaseViewHolder(view) {
        init {
            Log.d("Egor", "FriendRequestViewHolder init")
            view.btnApprove.setOnClickListener {
                onClick?.onClick(item, it)
            }
            view.btnCancel.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }
        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                GlideHelper.loadImage(view.context, it.image, view.imgPhoto, R.drawable.ic_account_circle)
                view.tvName.text = it.name
            }

        }

    }
}