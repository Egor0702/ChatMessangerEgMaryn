package com.example.chatmessangeregmaryn.presentation.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatmessangeregmaryn.domain.type.HandleOnce
import com.example.chatmessangeregmaryn.domain.type.Failure

abstract class BaseViewModel : ViewModel() {

    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData() // объект LiveData, содержащий ошибку(Failure).
                                                                             // Обернут в класс HandleOnce для предотвращения повторных отрисовок одних и тех же ошибок

    protected fun handleFailure(failure: Failure) { // сеттер присваивающий ошибку
        this.failureData.value = HandleOnce(failure)
    }
}