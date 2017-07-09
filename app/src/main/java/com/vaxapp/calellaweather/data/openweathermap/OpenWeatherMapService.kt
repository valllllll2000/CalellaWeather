package com.vaxapp.calellaweather.data.openweathermap

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface OpenWeatherMapService {

    @GET
    fun getWeatherInfo(@Url url: String): Observable<Response>
}