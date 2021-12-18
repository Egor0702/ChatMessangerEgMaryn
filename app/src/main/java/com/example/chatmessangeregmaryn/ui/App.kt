package com.example.chatmessangeregmaryn.ui

import android.app.Application
import com.example.chatmessangeregmaryn.presentation.injection.*
import dagger.Component
import com.example.chatmessangeregmaryn.ui.register.RegisterFragment
import com.example.chatmessangeregmaryn.ui.register.RegisterActivity
import com.example.chatmessangeregmaryn.ui.core.navigation.RouteActivity
import com.example.chatmessangeregmaryn.ui.home.ChatsFragment
import com.example.chatmessangeregmaryn.ui.home.HomeActivity
import com.example.chatmessangeregmaryn.ui.login.LoginFragment
import com.example.chatmessangeregmaryn.ui.service.FirebaseService
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()


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
    // в этих методах объявляем, где будут использоваться инъекция, описанная в модуле выше
    // activity
    fun inject(activity: RegisterActivity)
    fun inject(activity: HomeActivity)
    fun inject(activity: RouteActivity)
    //fragments
    fun inject(fragment: RegisterFragment)
    fun inject(fragment:LoginFragment)
    fun inject(fragment:ChatsFragment)
    fun inject(fragment: FriendsFragment)
    fun inject (fragment: FriendRequestFragment)
    //services
    fun inject(service: FirebaseService)

}