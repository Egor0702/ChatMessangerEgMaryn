package com.example.chatmessangeregmaryn.presentation.viewmodel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatmessangeregmaryn.domain.type.HandleOnce
import com.example.chatmessangeregmaryn.domain.type.Failure

abstract class BaseViewModel : ViewModel() {
    init {
        Log.d("Egor", "Всем хло, мы в BaseViewModel")
    }
    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData() // объект LiveData, содержащий ошибку(Failure).
                                                                             // Обернут в класс HandleOnce для предотвращения повторных отрисовок одних и тех же ошибок
    var progressData: MutableLiveData<Boolean> = MutableLiveData() // хранит состояние загрузки данных. Необходим для управления видимости прогресс бара

    protected fun handleFailure(failure: Failure) { // сеттер присваивающий ошибку
        Log.d("Egor", "BaseViewModel handleFailure()")
        this.failureData.value = HandleOnce(failure)
        updateProgress(false)
    }
    protected fun updateProgress(progress: Boolean) { // сеттер прогресс бара
        Log.d("Egor", "BaseViewModel updateProgress()")
        this.progressData.value = progress
    }
}