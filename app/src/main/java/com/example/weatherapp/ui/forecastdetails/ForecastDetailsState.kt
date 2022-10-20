package com.example.weatherapp.ui.forecastdetails

import com.example.weatherapp.data.model.Daily

sealed class ForecastDetailsState {
    class Failure(val msg: Throwable) : ForecastDetailsState()
    class Success(val daily: Daily) : ForecastDetailsState()
    object Empty : ForecastDetailsState()
}