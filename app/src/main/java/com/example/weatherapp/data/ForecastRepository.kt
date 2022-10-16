package com.example.weatherapp.data

import com.example.weatherapp.data.api.NetworkModule
import com.example.weatherapp.data.api.WeatherService
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Location

class ForecastRepository {

    private val service = NetworkModule.getInstance().create(WeatherService::class.java)

    suspend fun getForecast(latitude: String, longitude: String): Forecast {
        return service.getForecast(latitude, longitude)
    }

    suspend fun getLocation(zipCode: String): Location {
        return service.getZipCode(zipCode)
    }

}