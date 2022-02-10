package com.example.chatmessangeregmaryn.ui.account

import android.os.Bundle
import android.util.Log
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.core.BaseActivity
import com.example.chatmessangeregmaryn.ui.core.BaseFragment

class AccountActivity : BaseActivity() {
    override var fragment: BaseFragment = AccountFragment()
    init {
        Log.d("Egor", "Всем хло, мы в AccountActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "AccountActivity onCreate()")
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}
