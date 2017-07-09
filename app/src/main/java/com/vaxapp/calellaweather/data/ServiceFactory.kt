package com.vaxapp.calellaweather.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    fun <T> createRetrofitService(url: String, clazz: Class<T>): T {
        val client = OkHttpClient()
        val retrofit = Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(clazz)
    }
}