package com.vaxapp.calellaweather.data.openweathermap

data class Response(
        val coord: Coord,
        val weather: List<Weather>,
        val main: Main,
        val wind: Wind,
        val name: String

)

data class Coord(
        val lat: Double,
        val lon: Double
)

data class Weather(
        val description: String
)

data class Main(
        val temp: Double,
        val pressure: Double,
        val humidity: Double,
        val temp_min: Double,
        val temp_max: Double
)

data class Wind(
        val speed: Double
)