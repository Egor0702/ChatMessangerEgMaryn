package com.example.chatmessangeregmaryn.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.FragmentLoginBinding
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess
import com.example.chatmessangeregmaryn.ui.fragment.BaseFragment

class LoginFragment () : BaseFragment(){

    override val layoutId: Int = R.layout.fragment_login
    override val titleToolbar = R.string.screen_login
    private lateinit var fragmentLoginBinding : FragmentLoginBinding
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(accountData, ::renderAccount)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = fragmentLoginBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoginBinding.btnLogin.setOnClickListener {
            Log.d("Egor", "In btnLogin")
            validateFields()
        }

        fragmentLoginBinding.btnRegister.setOnClickListener {
            activity?.let {  navigator.showSignUp(it)}
        }
    }

    private fun validateFields() {
        hideSoftKeyboard()
        val allFields = arrayOf(fragmentLoginBinding.etEmail, fragmentLoginBinding.etPassword)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        if (allValid) {
            login(fragmentLoginBinding.etEmail.text.toString(), fragmentLoginBinding.etPassword.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        showProgress()
        accountViewModel.login(email, password)
    }

    private fun renderAccount(account: AccountEntity?) {
        hideProgress()
        activity?.let {
            navigator.showHome(it)
            it.finish()
        }
    }
}