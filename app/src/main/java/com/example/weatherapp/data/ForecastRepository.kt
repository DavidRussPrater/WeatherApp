package com.example.weatherapp.data

import android.util.Log
import com.example.weatherapp.data.api.NetworkModule
import com.example.weatherapp.data.api.WeatherService
import com.example.weatherapp.data.model.Daily
import com.example.weatherapp.data.model.Location
import com.example.weatherapp.data.persistence.WeatherDao

class ForecastRepository(private val weatherDao: WeatherDao) {

    private val service = NetworkModule.getInstance().create(WeatherService::class.java)

    suspend fun getForecast(latitude: String, longitude: String): List<Daily> {
        val daily = service.getForecast(latitude, longitude).daily.map {
            Daily.createDaily(it)
        }
        insertIntoDatabase(daily)
        return daily
    }

    suspend fun getLocation(zipCode: String): Location {
        return service.getZipCode(zipCode)
    }

    private suspend fun insertIntoDatabase(daily: List<Daily>) {
        try {
            weatherDao.insertDaily(daily)
        } catch (e: Exception) {
            Log.e("Error DB: ", e.message!!)
        }
    }

}