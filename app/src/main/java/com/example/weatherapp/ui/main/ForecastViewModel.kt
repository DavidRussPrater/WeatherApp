package com.example.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(private val repository: ForecastRepository) : ViewModel() {

    private val _forecast = MutableStateFlow<ForecastState>(ForecastState.Empty)
    val forecast: StateFlow<ForecastState> = _forecast

    var latitude = ""
    var longitude = ""

    fun getForecast(latitude: String, longitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getForecast(latitude, longitude)
                _forecast.value = ForecastState.ForecastSuccess(response)
            } catch (e: java.lang.Exception) {
                Log.e("Error: ", e.message!!, e)
                _forecast.value = ForecastState.Failure(e)
            }
        }
    }

    fun getLocation(zipCode: String) {
        _forecast.value = ForecastState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getLocation(zipCode)
                _forecast.value = ForecastState.LocationSuccess(response)
            } catch (e: java.lang.Exception) {
                Log.e("Error: ", e.message!!, e)
                _forecast.value = ForecastState.Failure(e)
            }
        }
    }

}