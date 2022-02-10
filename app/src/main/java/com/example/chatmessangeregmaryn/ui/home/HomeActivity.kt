package com.example.chatmessangeregmaryn.ui.home

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.chatmessangeregmaryn.databinding.ActivityNavigationBinding
import com.example.chatmessangeregmaryn.domain.friends.FriendEntity
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.presentation.viewmodel.FriendsViewModel
import com.example.chatmessangeregmaryn.ui.core.BaseActivity
import com.example.chatmessangeregmaryn.ui.core.BaseFragment
import com.example.chatmessangeregmaryn.ui.core.inTransaction
import com.example.chatmessangeregmaryn.ui.firebase.NotificationHelper
import com.example.chatmessangeregmaryn.ui.friends.FriendRequestsFragment
import com.example.chatmessangeregmaryn.ui.friends.FriendsFragment
import javax.inject.Inject


class HomeActivity : BaseActivity() {
    init {
        Log.d("Egor", "Всем хло, мы в HomeActivity")
    }
    override var fragment: BaseFragment = ChatsFragment()
    override val contentId = R.layout.activity_navigation
    private lateinit var activityNavigationBinding: ActivityNavigationBinding

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Egor", "onCreate() in HomeActivity после вызова onCreate() супер-класса")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        Log.d("Egor", "HomeActivity до инъекции")

        App.appComponent.inject(this) // получает все зависимости, в том числе и для BaseActivity

        Log.d("Egor", "HomeActivity после инъекции")

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

        val type: String? = intent.getStringExtra("type")
        when (type) {
            NotificationHelper.TYPE_ADD_FRIEND -> {
                openDrawer()
                friendsViewModel.getFriendRequests()
                activityNavigationBinding.navigation.requestContainer.visibility = View.VISIBLE
            }
        }

        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu) // устанавливаем изображение для открытия меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // устанавливаем видимость установленного выше изображения
        Log.d("Egor", "после supportActionBar")

        activityNavigationBinding.navigation.btnLogout.setOnClickListener { // устанавливаем слушателя для кнопки "Выйти"
            accountViewModel.logout()
        }
        activityNavigationBinding.navigation.btnChats.setOnClickListener { // устанавливаем слушателя для кнопки "Чаты"
            replaceFragment(ChatsFragment()) // устанавливаем на экране пользователя фрагмент с чатами
            closeDrawer()
        }
        activityNavigationBinding.navigation.btnAddFriend.setOnClickListener { // устанавливаем слушателя для конпки "Пригласить друга"
            if (activityNavigationBinding.navigation.containerAddFriend.visibility == View.VISIBLE) { // если упользователя открыта строка, где он может найти другого пользователя, указав его эмейл, то:
                activityNavigationBinding.navigation.containerAddFriend.visibility = View.GONE // делаем эту строку невидимой. GONE означает, что для нее не будет оставлено место, как будто ее и не существует
            } else {
                activityNavigationBinding.navigation.containerAddFriend.visibility = View.VISIBLE // если указанная строка невидима, делаем ее видимой
            }
        }
        activityNavigationBinding.navigation.btnAdd.setOnClickListener { // устанавливаем слушателя для кнопки "Отправить запрос"
            hideSoftKeyboard() // убираем клаву
            showProgress() // показываем прогреесс бар
            friendsViewModel.addFriend(activityNavigationBinding.navigation.etEmail.text.toString()) // осуществляем запрос
        }
        activityNavigationBinding.navigation.btnFriends.setOnClickListener { // устанавливаем слушателя на кнопку "Друзья"
            replaceFragment(FriendsFragment()) // меняем фрагмент, который отображается на экране
            closeDrawer() // закрываем меню
        }
        Log.d("Egor", "перед supportFragment")

        supportFragmentManager.inTransaction { replace(R.id.requestContainer, FriendRequestsFragment()) } // доюавляем в контейнер фрагмент со списком входящих заявок в друзья

        activityNavigationBinding.navigation.btnRequests.setOnClickListener { // устанавливае  слушателя кнопки "Входящие приглашения"
            friendsViewModel.getFriendRequests()
            if ( activityNavigationBinding.navigation.requestContainer.visibility == View.VISIBLE) {
                activityNavigationBinding.navigation.requestContainer.visibility = View.GONE
            } else {
                activityNavigationBinding.navigation.requestContainer.visibility = View.VISIBLE
            }
        }
        activityNavigationBinding.navigation.profileContainer.setOnClickListener {
            navigator.showAccount(this)
            Handler(Looper.getMainLooper()).postDelayed({
                closeDrawer()
            }, 200)
        }
    }

    override fun onResume() {
        super.onResume()
        accountViewModel.getAccount()
    }

    override fun setupContent() {
        Log.d("Egor", "setupContent() HomeActivity")
        activityNavigationBinding = DataBindingUtil.setContentView(this, contentId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Egor", "HomeActivity onOptionsItemSelected()")
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
    private fun openDrawer() { // метод, которыцй должен открыть меню
        Log.d("Egor", "HomeActivity openDrawer()")
        hideSoftKeyboard() // убираем клавитатуру, в случае если клавиатура открыта
        activityNavigationBinding.drawerLayout.openDrawer(activityNavigationBinding.navigation.navigationView) // открываем меню
    }

    private fun closeDrawer() { // метод, который призван закрыть панель навигации при выборе одного из пунктов менб
        Log.d("Egor", "HomeActivity closeDrawer()")
        hideSoftKeyboard() // убираем клавитатуру, в случае если клавиатура открыта
        activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView) // закрываем меню
    }

    private fun handleAccount(accountEntity: AccountEntity?) { // получаем от сервера данные, преобразуем их в объект accounEntitiy и из этого объекта берем данные
        Log.d("Egor", "HomeActivity handleAccount()")
        accountEntity?.let {
            activityNavigationBinding.navigation.tvUserName.text = it.name // заполняем данные об имени пользователя в меню
            activityNavigationBinding.navigation.tvUserEmail.text = it.email // заполняем данные об эмейл
            activityNavigationBinding.navigation.tvUserStatus.text = it.status // заполняем информацию о статусе пользователя
            activityNavigationBinding.navigation.tvUserStatus.visibility = if (it.status.isNotEmpty()) View.VISIBLE else View.GONE // часто может быть ситуация, когда статус будет пуст. В этом случае, мы его скрываем
        }
    }

    private fun handleLogout(none: None?) { // метод срабатывает, когда изменится значение logoutData в AccountViewModel (когда отработала бэкэнд часть для выхода их аккаунта)
        Log.d("Egor", "HomeActivity handleLogout()")
        navigator.showLogin(this) // вызываем метод, который выведет на экран страницу с входом в аккаунт
        finish() // закрываем данное активити
    }
    private fun handleAddFriend(none: None?) { // метод сработатет, когда изменится значение addFriendData в FriendsViewModel
        Log.d("Egor", "HomeActivity handleAddFriend()")
        activityNavigationBinding.navigation.etEmail.text.clear() // удаляем текст в эмейлом пользователя, которого мы искали
        activityNavigationBinding.navigation.containerAddFriend.visibility = View.GONE // убираем вкладку с поиском друзей

        hideProgress() // закрывает клавиатуру
        showMessage("Запрос отправлен.") // выводим тост
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        Log.d("Egor", "HomeActivity handleFriendRequests()")
        if (requests?.isEmpty() == true) { // в случае, если нам вернулся пустой список входящих запросов в друзбя, выполняется следующий код:
            activityNavigationBinding.navigation.requestContainer.visibility = View.GONE // раздел, где расположен спимок друзей, невидим
            if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) { // в случае, если меню лткрыто, то
                showMessage("Нет входящих приглашений.") // выводим тост, о том, что нет входящих сообщений
            }
        }
    }

    override fun handleFailure(failure: Failure?) { // обрабабтывае ошибки
        Log.d("Egor", "HomeActivity handleFailure()")
        hideProgress() // запускаем анимацию загрузки
        when (failure) {
            Failure.ContactNotFoundError -> navigator.showEmailNotFoundDialog(this, activityNavigationBinding.navigation.etEmail.text.toString()) // в случае, еси мы получаем ошибку при добавлении в друзья несуществующего пользователя, то вызываем метод
            else -> super.handleFailure(failure) // в иных случаях передаем выполнение в родительский класс
        }
    }

    override fun onBackPressed() { // переопределяем системную кнопку "Назад"
        Log.d("Egor", "HomeActivity onBackPressed()")
        if (activityNavigationBinding.drawerLayout.isDrawerOpen(activityNavigationBinding.navigation.navigationView)) { // условие - если у нас отрыто меню, тогда системная кнопка "Назад"
            activityNavigationBinding.drawerLayout.closeDrawer(activityNavigationBinding.navigation.navigationView) // закрывает меню
        } else {
            super.onBackPressed() // в обратном случае вызываем аналогичный метод у BaseActivity
        }
    }
}