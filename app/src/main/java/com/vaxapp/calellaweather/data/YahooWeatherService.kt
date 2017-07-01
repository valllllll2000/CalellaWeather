package com.vaxapp.calellaweather.data

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface YahooWeatherService {

    @GET
    fun getWeatherInfo(@Url url: String): io.reactivex.Observable<Response>

    /**
     * Companion object to create the YahooWeatherService
     */
    companion object Factory {
        fun create(): YahooWeatherService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://query.yahooapis.com/v1/public/yql/")
                    .build()

            return retrofit.create(YahooWeatherService::class.java);
        }
    }
}