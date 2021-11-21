package com.example.chatmessangeregmaryn.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.FragmentRegisterBinding
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess
import javax.inject.Inject

class RegisterFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register
    lateinit var fragmentRegisterBinding: FragmentRegisterBinding
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "In registerFragment")
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(registerData, ::handleRegister)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = fragmentRegisterBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentRegisterBinding.btnNewMembership.setOnClickListener {

            register()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun validateFields(): Boolean {
        val allFields = arrayOf(fragmentRegisterBinding.etEmail, fragmentRegisterBinding.etPassword, fragmentRegisterBinding.etConfirmPassword, fragmentRegisterBinding.etusername)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid && validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        val valid = fragmentRegisterBinding.etPassword.text.toString() == fragmentRegisterBinding.etConfirmPassword.text.toString()
        if (!valid) {
            showMessage(getString(R.string.error_password_mismatch))
        }
        return valid
    }

    private fun register() {
        hideSoftKeyboard()

        val allValid = validateFields()

        if (allValid) {
            showProgress()

            accountViewModel.register(
                    fragmentRegisterBinding.etEmail.text.toString(),
                    fragmentRegisterBinding.etusername.text.toString(),
                    fragmentRegisterBinding.etPassword.text.toString()
            )
        }
    }

    private fun handleRegister(none: None? = None()) {
        hideProgress()
        showMessage("Аккаунт создан")
    }
}