package com.example.chatmessangeregmaryn.ui.core.navigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chatmessangeregmaryn.ui.App
import javax.inject.Inject

class RouteActivity : AppCompatActivity() { // класс нужен для того, чтобы запустить механизм, который определит, что вывести на экран
                                             // вход в аккаунт или же домашнее окно приложения
    init {
        Log.d("Egor", "Всем хло, мы в RouteActivity")
    }
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Egor", "RouteActivity onCreate()")
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this) // получили все зависимости из AppComponent и можем обращаться к navigator
        navigator.showMain(context = this)
    }
}