package com.example.chatmessangeregmaryn.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.activity.BaseActivity
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess
import androidx.databinding.DataBindingUtil
import com.example.chatmessangeregmaryn.databinding.ActivityNavigation1Binding


class HomeActivity : BaseActivity() {
    init {
        Log.d("Egor", "Всем хло, мы в HomeActivity")
    }
    override val fragment = ChatsFragment()
    override val contentId = R.layout.activity_navigation1
    private lateinit var activityNavigationBinding: ActivityNavigation1Binding

    private lateinit var accountViewModel: AccountViewModel

    override fun setupContent() {
        activityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(accountData, ::handleAccount)
            onSuccess(logoutData, ::handleLogout)
            onFailure(failureData, ::handleFailure)
        }

        accountViewModel.getAccount()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // переопределяем кнопку домой
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // устанавливаем видимость для кнопки "Меню"

        activityNavigationBinding.navigation.btnLogout.setOnClickListener {
            accountViewModel.logout()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (
                        activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) {
                    activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView)
                } else {
                    activityNavigationBinding.drawerLayout.openDrawer(activityNavigationBinding.navigation.navigationView)
                }
            }
        }

        return super.onOptionsItemSelected(item)
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

    override fun onBackPressed() {
        if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) {
            activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView)
        } else {
            super.onBackPressed()
        }
    }
}