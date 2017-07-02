package com.vaxapp.calellaweather.data.yahoo

import com.vaxapp.calellaweather.data.wunderground.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface WundergroundService {

    @GET
    fun getWeatherInfo(@Url url: String): io.reactivex.Observable<Response>
}