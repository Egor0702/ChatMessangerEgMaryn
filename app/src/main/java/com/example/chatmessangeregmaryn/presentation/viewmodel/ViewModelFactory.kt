package com.example.chatmessangeregmaryn.presentation.viewmodel

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
         @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{ // ViewModelProvider.Factory отвечает за создание viewModel
    init {
        Log.d("Egor", "Всем хло, мы в ViewModelFactory")
    }
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T { // данный метод отвечает за создание viewModel
        Log.d("Egor", "ViewModelFactory create()")
        val creator = creators[modelClass] ?: creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknow model class $modelClass")
        try{
            //@Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }catch (e : Exception){
            throw RuntimeException(e)
        }
    }
}