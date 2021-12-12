package com.example.chatmessangeregmaryn.ui.home

import android.os.Bundle
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.ui.fragment.BaseFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChatsFragment : BaseFragment() {
    init {
        Log.d("Egor", "Всем хло, мы в ChatsFragment")
    }
    override val titleToolbar = R.string.chats
    override val layoutId = R.layout.fragment_chats

}