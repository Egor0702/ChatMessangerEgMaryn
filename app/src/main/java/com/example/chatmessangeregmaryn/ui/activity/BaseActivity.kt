package com.example.chatmessangeregmaryn.ui.activity

import android.app.Activity
import androidx.fragment.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatmessangeregmaryn.R
import com.example.chatmessangeregmaryn.databinding.ActivityLayoutBinding
import com.example.chatmessangeregmaryn.databinding.ToolbarBinding
import com.example.chatmessangeregmaryn.domain.type.Failure
import com.example.chatmessangeregmaryn.ui.core.navigation.Navigator
import com.example.chatmessangeregmaryn.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {
    abstract val fragment: BaseFragment
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator
    lateinit var activityLayoutBinding: ActivityLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLayoutBinding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(activityLayoutBinding.root) //устанавливаем общий для всех layout


        setSupportActionBar(activityLayoutBinding.toolbar) // устанавливаем тул бар (без findViewById)
        addFragment(savedInstanceState) // добавляем фрагмент
    }

    override fun onBackPressed() { //обработка нажатия пользователем кнопки "Назад"
        (supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
        ) as BaseFragment).onBackPressed() // вызываем этот же метод у фрагмента
        super.onBackPressed()
    }

    fun addFragment(savedInstanceState: Bundle?) { // добавляем фрагмент
        savedInstanceState ?: supportFragmentManager.inTransaction { //здесь ли бо добавляем, либо удаляем фрагмент
            add(R.id.fragmentContainer, fragment)
        }
    }


    fun showProgress() = progressStatus(View.VISIBLE) // передаем параметр, который устанавливает видимость для экрана загрузки

    fun hideProgress() = progressStatus(View.GONE) // передаем параметр, который устанавливает, что экран загрузки не видим

    fun progressStatus(viewStatus: Int) { // устанавливаем видимость/невидимость экрана загрузки
        Log.d("Egor", "progressStatus")
        activityLayoutBinding.toolbarProgressBar.visibility = viewStatus
    }


    fun hideSoftKeyboard() { // скрываем клавиатуру
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    fun handleFailure(failure: Failure?) { // в зависимости от типа ошибки определяем содержимое Тоста
        hideProgress() // убираем экран загрузки
        when (failure) {
            is Failure.NetworkConnectionError -> showMessage(getString(R.string.error_network))
            is Failure.ServerError -> showMessage(getString(R.string.error_server))
            is Failure.EmailAlreadyExistError -> showMessage(getString(R.string.error_email_already_exist))
            is Failure.AuthError -> showMessage(getString(R.string.error_auth))
            is Failure.TokenError -> navigator.showLogin(this)
        }
    }

    fun showMessage(message: String) { // выводим сообщение об ошибке
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory).get(T::class.java)
        vm.body()
        return vm
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun Activity?.base(block: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.let(block)
}
