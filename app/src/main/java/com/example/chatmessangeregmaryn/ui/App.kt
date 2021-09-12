package com.example.chatmessangeregmaryn.ui

import android.app.Application
import android.util.Log
import dagger.Component
import com.example.chatmessangeregmaryn.ui.fragment.RegisterFragment
import com.example.chatmessangeregmaryn.presentation.injection.AppModule
import com.example.chatmessangeregmaryn.presentation.injection.CacheModule
import com.example.chatmessangeregmaryn.presentation.injection.RemoteModule
import com.example.chatmessangeregmaryn.presentation.injection.ViewModelModule
import com.example.chatmessangeregmaryn.ui.activity.RegisterActivity
import com.example.chatmessangeregmaryn.ui.service.FirebaseService
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Egor", "App")

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    //activities

    fun inject(activity: RegisterActivity) // в этих методах объявляем, где будут использоваться инъекция, описанная в модуле выше

    //fragments
    fun inject(fragment: RegisterFragment) // в этих методах объявляем, где будут использоваться инъекция, описанная в модуле выше

    //services
    fun inject(service: FirebaseService) // в этих методах объявляем, где будут использоваться инъекция, описанная в модуле выше

}