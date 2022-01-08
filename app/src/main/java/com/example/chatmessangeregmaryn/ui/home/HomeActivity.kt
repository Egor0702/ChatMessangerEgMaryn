package com.example.chatmessangeregmaryn.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess
import androidx.databinding.DataBindingUtil
import com.example.chatmessangeregmaryn.databinding.ActivityNavigation1Binding
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.presentation.viewmodel.FriendsViewModel
import com.example.chatmessangeregmaryn.ui.core.BaseActivity
import com.example.chatmessangeregmaryn.ui.core.BaseFragment
import com.example.chatmessangeregmaryn.ui.friends.FriendRequestsFragment
import com.example.chatmessangeregmaryn.ui.friends.FriendsFragment
import kotlinx.android.synthetic.main.activity_navigation1.*
import kotlinx.android.synthetic.main.navigation.*
import javax.inject.Inject


class HomeActivity : BaseActivity() {
    init {
        Log.d("Egor", "Всем хло, мы в HomeActivity")
    }
    override var fragment: BaseFragment = ChatsFragment()
    override val contentId = R.layout.activity_navigation1
    private lateinit var activityNavigationBinding: ActivityNavigation1Binding

    private lateinit var accountViewModel: AccountViewModel
    @Inject
    lateinit var friendsViewModel: FriendsViewModel

    override fun setupContent() {
        activityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(accountData, ::handleAccount)
            onSuccess(logoutData, ::handleLogout)
            onFailure(failureData, ::handleFailure)
        }

        friendsViewModel = viewModel {
            onSuccess(addFriendData, ::handleAddFriend)
            onSuccess(friendRequestsData, ::handleFriendRequests)
            onFailure(failureData, ::handleFailure)
        }

        accountViewModel.getAccount()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // переопределяем кнопку домой
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // устанавливаем видимость для кнопки "Меню"

        activityNavigationBinding.navigation.btnLogout.setOnClickListener {
            accountViewModel.logout()
        }
        activityNavigationBinding.navigation.btnChats.setOnClickListener {
            replaceFragment(ChatsFragment())
            closeDrawer()
        }
        activityNavigationBinding.navigation.btnAddFriend.setOnClickListener {
            if (containerAddFriend.visibility == View.VISIBLE) {
                containerAddFriend.visibility = View.GONE
            } else {
                containerAddFriend.visibility = View.VISIBLE
            }
        }
        activityNavigationBinding.navigation.btnAdd.setOnClickListener {
            hideSoftKeyboard()
            showProgress()
            friendsViewModel.addFriend(etEmail.text.toString())
        }
        activityNavigationBinding.navigation.btnFriends.setOnClickListener {
            replaceFragment(FriendsFragment())
            closeDrawer()
        }
        supportFragmentManager.beginTransaction().replace(R.id.requestContainer, FriendRequestsFragment()).commit()
        activityNavigationBinding.navigation.btnRequests.setOnClickListener {
            friendsViewModel.getFriendRequests()
            if (requestContainer.visibility == View.VISIBLE) {
                requestContainer.visibility = View.GONE
            } else {
                requestContainer.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) {
                    activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView)
                } else {
                    activityNavigationBinding.drawerLayout.openDrawer(activityNavigationBinding.navigation.navigationView)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun openDrawer() {
        hideSoftKeyboard()
        drawerLayout.openDrawer(navigationView)
    }

    private fun closeDrawer() {
        hideSoftKeyboard()
        drawerLayout.closeDrawer(navigationView)
    }

    private fun handleAccount(accountEntity: AccountEntity?) {
        accountEntity?.let {
            activityNavigationBinding.navigation.tvUserName.text = it.name
            activityNavigationBinding.navigation.tvUserEmail.text = it.email
            activityNavigationBinding.navigation.tvUserStatus.text = it.status
            activityNavigationBinding.navigation.tvUserStatus.visibility = if (it.status.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }
    private fun handleAddFriend(none: None?) {
        activityNavigationBinding.navigation.etEmail.text.clear()
        activityNavigationBinding.navigation.containerAddFriend.visibility = View.GONE

        hideProgress()
        showMessage("Запрос отправлен.")
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        if (requests?.isEmpty() == true) {
            activityNavigationBinding.navigation.requestContainer.visibility = View.GONE
            if (drawerLayout.isDrawerOpen(navigationView)) {
                showMessage("Нет входящих приглашений.")
            }
        }
    }


    override fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            Failure.ContactNotFoundError -> showEmailNotFoundDialog()
            else -> super.handleFailure(failure)
        }
    }


    private fun showEmailNotFoundDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.message_promt_app))

            .setPositiveButton(android.R.string.yes) { dialog, which ->
                navigator.showEmailInvite(this, activityNavigationBinding.navigation.etEmail.text.toString())
            }

            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
    override fun onBackPressed() {
        if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) {
            activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView)
        } else {
            super.onBackPressed()
        }
    }
}