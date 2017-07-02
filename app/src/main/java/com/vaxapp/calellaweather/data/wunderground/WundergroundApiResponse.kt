package com.vaxapp.calellaweather.data.wunderground

data class Response(
        val current_observation: CurrentObservation
)

data class CurrentObservation(
        val display_location: DisplayLocation,
        val weather: String,
        val temp_c: Double,
        val relative_humidity: String

)

data class DisplayLocation(
        val state: String,
        val city: String,
        val country_iso3166: String,
        val latitude: String,
        val longitude: String
)