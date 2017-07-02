package com.vaxapp.calellaweather.data.yahoo

import retrofit2.http.GET
import retrofit2.http.Url

interface YahooWeatherService {

    @GET
    fun getWeatherInfo(@Url url: String): io.reactivex.Observable<Response>
}