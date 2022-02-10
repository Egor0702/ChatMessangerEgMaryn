package com.example.chatmessangeregmaryn.ui.ext

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.chatmessangeregmaryn.domain.type.HandleOnce
import com.example.chatmessangeregmaryn.domain.type.Failure

 fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L,  body: (T?) -> Unit) {
    Log.d("Egor", "LifeCycle onSuccess()")
    liveData.observe(this, Observer(body))
}

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(liveData: L, body: (Failure?) -> Unit) {
    Log.d("Egor", "LifeCycle onFailure()")
    liveData.observe(this, Observer {
        it.getContentIfNotHandled()?.let(body)
    })
}