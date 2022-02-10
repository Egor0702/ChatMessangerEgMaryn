package com.example.chatmessangeregmaryn.ui.login

import android.util.Log
import com.example.chatmessangeregmaryn.ui.core.BaseActivity
import com.example.chatmessangeregmaryn.ui.core.BaseFragment


class LoginActivity : BaseActivity() {
    init {
        Log.d("Egor", "Всем хло, мы в LoginActivity")
    }
    override var fragment: BaseFragment = LoginFragment()
}