package com.vaxapp.calellaweather.data.yahoo

data class Response(
        val query: Query
)

data class Query(
        val count: Int,
        val lang: String,
        val results: Results
)

data class Results(
        val channel: Channel
)

data class Channel(
        val units: Units,
        val title: String,
        val wind: Wind,
        val atmosphere: Atmosphere,
        val item: Item
)

data class Units(
        val distance: String,
        val pressure: String,
        val speed: String,
        val temperature: String
)

data class Wind(
        val speed: String
)

data class Atmosphere(
        val humidity: String
)

data class Item(
        val lat: String,
        val long: String,
        val condition: Condition
)

data class Condition(
        val date: String,
        val temp: String,
        val text: String
)