package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.extensions.capitalizeWords
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Entity(tableName = "daily")
data class Daily(
    @PrimaryKey
    val time: Long,
    val date: String,
    val clouds: Int,
    val dewPoint: Int,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val tempMin: Int,
    val tempMax: Int,
    val uvi: Int,
    val weather: String,
    val windDeg: Int,
    val windGust: Int,
    val windSpeed: Int
) : Serializable {

    companion object {
        private val sdf = SimpleDateFormat("MMMM d, y", Locale.US)

        fun createDaily(dto: ForecastDTO.Daily): Daily {
            return Daily(
                dto.dt,
                sdf.format(dto.dt * 1000L),
                dto.clouds,
                dto.dewPoint.roundToInt(),
                dto.humidity,
                (dto.pop * 100).roundToInt(),
                dto.pressure,
                dto.sunrise,
                dto.sunset,
                dto.temp.min.roundToInt(),
                dto.temp.max.roundToInt(),
                dto.uvi.roundToInt(),
                dto.weather.first().description.capitalizeWords(),
                dto.windDeg,
                dto.windGust.roundToInt(),
                dto.windSpeed.roundToInt()
            )
        }
    }
}
