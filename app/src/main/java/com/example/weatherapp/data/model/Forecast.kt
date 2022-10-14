package com.example.weatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Daily>,
    val message: Int
) {
    @JsonClass(generateAdapter = true)
    data class City(
        val coord: Coordinates,
        val country: String,
        val id: Int,
        val name: String,
        val population: Int,
        val sunrise: Int,
        val sunset: Int,
        val timezone: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Coordinates(
            val lat: Double,
            val lon: Double
        )
    }

    @JsonClass(generateAdapter = true)
    data class Daily (
        val clouds: Clouds,
        val dt: Int,
        @Json(name = "dt_txt")
        val dtTxt: String,
        val main: Main,
        val pop: Double,
        val sys: Sys,
        val visibility: Int,
        val weather: List<Weather>,
        val wind: Wind
    ) {
        @JsonClass(generateAdapter = true)
        data class Clouds(
            val all: Int
        )

        @JsonClass(generateAdapter = true)
        data class Main(
            @Json(name = "feels_like")
            val feelsLike: Double,
            @Json(name = "grnd_level")
            val grndLevel: Int,
            val humidity: Int,
            val pressure: Int,
            @Json(name = "sea_level")
            val seaLevel: Int,
            val temp: Double,
            @Json(name = "temp_kf")
            val tempKf: Double,
            @Json(name = "temp_max")
            val tempMax: Double,
            @Json(name = "temp_min")
            val tempMin: Double
        )

        @JsonClass(generateAdapter = true)
        data class Sys(
            val pod: String
        )

        @JsonClass(generateAdapter = true)
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )

        @JsonClass(generateAdapter = true)
        data class Wind(
            val deg: Int,
            val gust: Double,
            val speed: Double
        )
    }
}