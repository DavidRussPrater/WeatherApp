package com.example.weatherapp.ui.main

import com.example.weatherapp.data.model.Daily
import com.example.weatherapp.data.model.Location

sealed class ForecastState {
    object Loading : ForecastState()
    class Failure(val msg: Throwable) : ForecastState()
    class ForecastSuccess(val dailyList: List<Daily>) : ForecastState()
    class LocationSuccess(val location: Location) : ForecastState()
    object Empty : ForecastState()
}
