package com.example.chatmessangeregmaryn.ui.home

import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.ui.core.BaseFragment
import android.util.Log

class ChatsFragment : BaseFragment() {
    init {
        Log.d("Egor", "Всем хло, мы в ChatsFragment")
    }
    override val titleToolbar = R.string.chats
    override val layoutId = R.layout.fragment_chats

}