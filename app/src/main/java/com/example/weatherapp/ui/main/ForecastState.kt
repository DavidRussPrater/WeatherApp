package com.example.weatherapp.ui.main

import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Location

sealed class ForecastState {
    object Loading : ForecastState()
    class Failure(val msg: Throwable) : ForecastState()
    class ForecastSuccess(val forecast: Forecast) : ForecastState()
    class LocationSuccess(val location: Location) : ForecastState()
    object Empty : ForecastState()
}
