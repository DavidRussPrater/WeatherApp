package com.example.weatherapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int
) {
    @JsonClass(generateAdapter = true)
    data class Current(
        val clouds: Int,
        @Json(name = "dew_point")
        val dewPoint: Double,
        val dt: Int,
        @Json(name = "feels_like")
        val feelsLike: Double,
        val humidity: Int,
        val pressure: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val uvi: Int,
        val visibility: Int,
        val weather: List<Weather>,
        @Json(name = "wind_deg")
        val windDeg: Int,
        @Json(name = "wind_gust")
        val windGust: Double?,
        @Json(name = "wind_speed")
        val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Daily(
        val clouds: Int,
        @Json(name = "dew_point")
        val dewPoint: Double,
        val dt: Long,
        @Json(name = "feels_like")
        val feelsLike: FeelsLike,
        val humidity: Int,
        @Json(name = "moon_phase")
        val moonPhase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Double?,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        @Json(name = "wind_deg")
        val windDeg: Int,
        @Json(name = "wind_gust")
        val windGust: Double?,
        @Json(name = "wind_speed")
        val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class FeelsLike(
            val day: Double,
            val eve: Double,
            val morn: Double,
            val night: Double
        )

        @JsonClass(generateAdapter = true)
        data class Temp(
            val day: Double,
            val eve: Double,
            val max: Double,
            val min: Double,
            val morn: Double,
            val night: Double
        )

        @JsonClass(generateAdapter = true)
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Hourly(
        val clouds: Int,
        @Json(name = "dew_point")
        val dewPoint: Double,
        val dt: Int,
        @Json(name = "feels_like")
        val feelsLike: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val temp: Double,
        val uvi: Double?,
        val visibility: Int,
        val weather: List<Weather>,
        @Json(name = "wind_deg")
        val windDeg: Int,
        @Json(name = "wind_gust")
        val windGust: Double?,
        @Json(name = "wind_speed")
        val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }
}