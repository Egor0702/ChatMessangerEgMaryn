package com.example.chatmessangeregmaryn.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.ActivityNavigationBinding
import com.example.chatmessangeregmaryn.databinding.FragmentAccountBinding
import com.example.chatmessangeregmaryn.databinding.FragmentLoginBinding
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.presentation.viewmodel.MediaViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.core.BaseFragment
import com.example.chatmessangeregmaryn.ui.core.GlideHelper
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess

class AccountFragment : BaseFragment() {
    init {
        Log.d("Egor", "Всем хло, мы в AccountActivity")
    }
    override val layoutId = R.layout.fragment_account

    override val titleToolbar = R.string.screen_account

    lateinit var accountViewModel: AccountViewModel
    lateinit var mediaViewModel: MediaViewModel
    private lateinit var fragmentAccountBinding: FragmentAccountBinding

    var accountEntity: AccountEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "AccountActivity onCreate()")
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(accountData, ::handleAccount)
            onSuccess(editAccountData, ::handleEditingAccount)
            onFailure(failureData, ::handleFailure)
        }

        mediaViewModel = viewModel {
            onSuccess(cameraFileCreatedData, ::onCameraFileCreated)
            onSuccess(pickedImageData, ::onImagePicked)
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Log.d("Egor", "LoginFragment onCreateView()")
        fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = fragmentAccountBinding.root
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Egor", "AccountActivity onActivityResult()")
        super.onActivityResult(requestCode, resultCode, data)

        mediaViewModel.onPickImageResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Egor", "AccountActivity onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        showProgress()

        accountViewModel.getAccount()

        fragmentAccountBinding.btnEdit.setOnClickListener {
            view.clearFocus()
            val fieldsValid = validateFields()
            if (!fieldsValid) {
                return@setOnClickListener
            }

            val passwordsValid = validatePasswords()
            if (!passwordsValid) {
                return@setOnClickListener
            }

            showProgress()

            val email = fragmentAccountBinding.etEmail.text.toString()
            val name = fragmentAccountBinding.etName.text.toString()
            val status = fragmentAccountBinding.etStatus.text.toString()
            val password = fragmentAccountBinding.etNewPassword.text.toString()

            accountEntity?.let {
                it.email = email
                it.name = name
                it.status = status

                if (password.isNotEmpty()) {
                    it.password = password
                }

                accountViewModel.editAccount(it)
            }
        }

        fragmentAccountBinding.imgPhoto.setOnClickListener {
            Log.d("Egor", "AccountFragment imgPhoto.setOnClickListener")
            base {
                navigator.showPickFromDialog(this) { fromCamera ->
                    if (fromCamera) {
                        mediaViewModel.createCameraFile()
                    } else {
                        navigator.showGallery(this)
                    }
                }
            }
        }
    }

    private fun validatePasswords(): Boolean {
        Log.d("Egor", "AccountActivity validatePasswords()")
        val currentPassword = fragmentAccountBinding.etCurrentPassword.text.toString()
        val newPassword = fragmentAccountBinding.etNewPassword.text.toString()

        return if (currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
            return if (currentPassword == accountEntity?.password) {
                true
            } else {
                showMessage(getString(R.string.error_wrong_password))
                false
            }
        } else if (currentPassword.isEmpty() && newPassword.isEmpty()) {
            true
        } else {
            showMessage(getString(R.string.error_empty_password))
            false
        }
    }

    private fun validateFields(): Boolean {
        Log.d("Egor", "AccountActivity validateFields()")
        hideSoftKeyboard()
        val allFields = arrayOf(fragmentAccountBinding.etEmail, fragmentAccountBinding.etName)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid
    }

    private fun handleAccount(account: AccountEntity?) {
        Log.d("Egor", "AccountActivity handleAccount()")
        hideProgress()
        accountEntity = account
        account?.let {
            GlideHelper.loadImage(requireActivity(), it.image, fragmentAccountBinding.imgPhoto)
            fragmentAccountBinding.etEmail.setText(it.email)
            fragmentAccountBinding.etName.setText(it.name)
            fragmentAccountBinding.etStatus.setText(it.status)

            fragmentAccountBinding.etCurrentPassword.setText("")
            fragmentAccountBinding.etNewPassword.setText("")
        }
    }


    private fun onCameraFileCreated(uri: Uri?) {
        Log.d("Egor", "AccountActivity onCameraFileCreated()")
        base {
            if (uri != null) {
                navigator.showCamera(this, uri)
            }
        }
    }

    private fun onImagePicked(pickedImage: MediaViewModel.PickedImage?) {
        Log.d("Egor", "AccountActivity onImagePicked()")
        if (pickedImage != null) {
            accountEntity?.image = pickedImage.string

            val placeholder = fragmentAccountBinding.imgPhoto.drawable
            Glide.with(this)
                    .load(pickedImage.bitmap)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .into(fragmentAccountBinding.imgPhoto)
        }
    }

    private fun handleEditingAccount(account: AccountEntity?) {
        Log.d("Egor", "AccountActivity handleEditingAccount()")
        showMessage(getString(R.string.success_edit_user))
        accountViewModel.getAccount()
    }


    private fun updateProgress(progress: Boolean?) {
        Log.d("Egor", "AccountActivity updateProgress()")
        if (progress == true) {
            fragmentAccountBinding.groupProgress.visibility = View.VISIBLE
        } else {
            fragmentAccountBinding.groupProgress.visibility = View.GONE
        }
    }
}