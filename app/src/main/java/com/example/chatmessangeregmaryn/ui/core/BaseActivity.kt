package com.example.chatmessangeregmaryn.ui.core

import android.app.Activity
import androidx.fragment.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatmessangeregmaryn.R

import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.ui.core.navigation.Navigator
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {
    init {
        Log.d("Egor", "Всем хло, мы в BaseActivity")
    }
    abstract var fragment: BaseFragment // var - чтобы обеспечить будущее изменение фрагмента
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var permissionManager: PermissionManager
    open val contentId = R.layout.activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "onCreate() in BaseActivity")
        super.onCreate(savedInstanceState)
        Log.d("Egor", "setupContent() вызывается BaseActivity")
        setupContent()
        Log.d("Egor", "setupContent() отработал BaseActivity")
        setSupportActionBar(toolbar) // устанавливаем тул бар (без findViewById)
        addFragment(savedInstanceState) // добавляем фрагмент
    }
    open fun setupContent() {
        Log.d("Egor", "setupContent() BaseActivity")
        setContentView(contentId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        fragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() { //обработка нажатия пользователем кнопки "Назад"
        Log.d("Egor", "onBackPressed() BaseActivity")
        (supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
        ) as BaseFragment).onBackPressed() // вызываем этот же метод у фрагмента
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.requestObject?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun addFragment(savedInstanceState: Bundle?, fragment: BaseFragment = this.fragment) { // добавляем фрагмент
        Log.d("Egor", "addFragment() BaseActivity")
        savedInstanceState ?: supportFragmentManager.inTransaction { //здесь либо добавляем, либо удаляем фрагмент
            add(R.id.fragmentContainer, fragment)
        }
    }

    fun replaceFragment(fragment: BaseFragment) { // замена фрагмента активити
        Log.d("Egor", "replaceFragment() BaseActivity")
        this.fragment = fragment // изменяем сохраненный в BaseActivity франмент на новый
        supportFragmentManager.inTransaction {
            replace(R.id.fragmentContainer, fragment) // замещаем имеющийся в фрагментконтейнере фрагмент новым
        }
    }

    fun showProgress() = progressStatus(View.VISIBLE) // передаем параметр, который устанавливает видимость для экрана загрузки

    fun hideProgress() = progressStatus(View.GONE) // передаем параметр, который устанавливает, что экран загрузки не видим

    fun progressStatus(viewStatus: Int) { // устанавливаем видимость/невидимость экрана загрузки
        Log.d("Egor", "BaseActivity progressStatus()")
        toolbar.toolbar_progress_bar.visibility = viewStatus
    }

    fun hideSoftKeyboard() { // скрываем клавиатуру
        Log.d("Egor", "BaseActivity hideSoftKeyboard()")
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    open fun handleFailure(failure: Failure?) { // в зависимости от типа ошибки определяем содержимое Тоста
        Log.d("Egor", "BaseActivity handleFailure()")
        hideProgress() // убираем экран загрузки
        when (failure) {
            is Failure.NetworkConnectionError -> showMessage(getString(R.string.error_network))
            is Failure.ServerError -> showMessage(getString(R.string.error_server))
            is Failure.EmailAlreadyExistError -> showMessage(getString(R.string.error_email_already_exist))
            is Failure.AuthError -> showMessage(getString(R.string.error_auth))
            is Failure.TokenError -> navigator.showLogin(this)
            is Failure.AlreadyFriendError -> showMessage(getString(R.string.error_already_friend))
            is Failure.AlreadyRequestedFriendError -> showMessage(getString(R.string.error_already_requested_friend))
            is Failure.ContactNotFoundError -> showMessage(getString(R.string.error_contact_not_found))
            is Failure.FilePickError -> showMessage(getString(R.string.error_picking_file))
        }
    }

    fun showMessage(message: String) { // выводим сообщение об ошибке
        Log.d("Egor", "BaseActivity showMessage()")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        Log.d("Egor", "BaseActivity viewmodel()")
        val vm: T = ViewModelProvider(this, viewModelFactory).get(T::class.java)
        Log.d("Egor", "BaseActivity viewModel перед ${vm.body()}")
        vm.body()
        return vm
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    Log.d("Egor", "BaseActivity base()")
    (this as? BaseActivity)?.let(block)
}
