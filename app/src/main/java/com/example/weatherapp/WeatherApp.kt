package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.ForecastRepository
import com.example.weatherapp.data.persistence.WeatherDatabase

// TODO Setup hilt for dependency injection
class WeatherApp : Application() {
    private val database by lazy { WeatherDatabase.getDatabase(this) }
    val repository by lazy { ForecastRepository(database.weatherDao()) }
}