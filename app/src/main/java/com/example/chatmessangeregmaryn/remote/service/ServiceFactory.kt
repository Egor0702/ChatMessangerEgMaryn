package com.example.chatmessangeregmaryn.remote.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {
    const val SERVER_URL = "http://chat.egor079d.beget.tech/chat/"
    const val BASE_URL = "$SERVER_URL/rest_api/"
    init {
        Log.d("Egor", "Всем хло, мы в ServiceFactory")
    }

    fun makeService(isDebug: Boolean): ApiService {
        Log.d("Egor", "ServiceFactory makeService(isDebug: Boolean)")
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        val gson = Gson()
        return makeService(okHttpClient, gson)
    }

    private fun makeService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        Log.d("Egor", "ServiceFactory makeService(okHttpClient: OkHttpClient, gson: Gson)")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        Log.d("Egor", "ServiceFactory makeOkHttpClient()")
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        Log.d("Egor", "ServiceFactory makeLoggingInterceptor()")
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}