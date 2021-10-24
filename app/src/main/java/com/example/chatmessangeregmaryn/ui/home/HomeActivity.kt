package com.example.chatmessangeregmaryn.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.ActivityNavigationBinding
import com.example.chatmessangeregmaryn.domain.account.AccountEntity
import com.example.chatmessangeregmaryn.domain.type.None
import com.example.chatmessangeregmaryn.presentation.viewmodel.AccountViewModel
import com.example.chatmessangeregmaryn.ui.App
import com.example.chatmessangeregmaryn.ui.activity.BaseActivity
import com.example.chatmessangeregmaryn.ui.ext.onFailure
import com.example.chatmessangeregmaryn.ui.ext.onSuccess


class HomeActivity : BaseActivity() {
    override val fragment = ChatsFragment()

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var activityNavigationBinding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        activityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(activityNavigationBinding.root)

        accountViewModel = viewModel {
            onSuccess(accountData, ::handleAccount)
            onSuccess(logoutData, ::handleLogout)
            onFailure(failureData, ::handleFailure)
        }

        accountViewModel.getAccount()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // переопределяем кнопку домой
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // устанавливаем видимость для кнопки "Меню"

        activityNavigationBinding.btnLogout.setOnClickListener {
            accountViewModel.logout()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (activityNavigationBinding.
                        drawerLayout.isDrawerOpen(activityNavigationBinding.navigationView)) {
                    activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigationView)
                } else {
                    activityNavigationBinding.drawerLayout.openDrawer(activityNavigationBinding.navigationView)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleAccount(accountEntity: AccountEntity?) {
        accountEntity?.let {
            activityNavigationBinding.tvUserName.text = it.name
            activityNavigationBinding.tvUserEmail.text = it.email
            activityNavigationBinding.tvUserStatus.text = it.status

            activityNavigationBinding.tvUserStatus.visibility = if (it.status.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun handleLogout(none: None?) {
        navigator.showLogin(this)
        finish()
    }

    override fun onBackPressed() {
        if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigationView)) {
            activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigationView)
        } else {
            super.onBackPressed()
        }
    }
}