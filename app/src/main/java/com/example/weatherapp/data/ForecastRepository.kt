package com.example.weatherapp.data

import com.example.weatherapp.data.api.NetworkModule
import com.example.weatherapp.data.api.WeatherService
import com.example.weatherapp.data.model.Daily
import com.example.weatherapp.data.model.Location
import com.example.weatherapp.data.persistence.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate

class ForecastRepository(private val weatherDao: WeatherDao) {

    private val service = NetworkModule.getInstance().create(WeatherService::class.java)

    suspend fun getForecast(latitude: String, longitude: String): List<Daily> {
        val dailyList = service.getForecast(latitude, longitude).daily.map { daily ->
            Daily.createDaily(daily)
        }
        insertIntoDatabase(dailyList)
        return dailyList
    }

    suspend fun getLocation(zipCode: String): Location {
        return service.getZipCode(zipCode)
    }

    fun getCachedForecastList(): Flow<List<Daily>> {
        return weatherDao.getDailyList().conflate()
    }

    fun getCachedForecast(key: Long): Flow<Daily> {
        return weatherDao.getDaily(key).conflate()
    }

    private suspend fun insertIntoDatabase(daily: List<Daily>) {
        weatherDao.insertDaily(daily)
    }
}