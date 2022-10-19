package com.example.weatherapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ForecastViewModel(private val repository: ForecastRepository) : ViewModel() {

    private val _forecast = MutableStateFlow<ForecastState>(ForecastState.Empty)
    val forecast: StateFlow<ForecastState> = _forecast

    fun getForecast(latitude: String, longitude: String) {
        _forecast.value = ForecastState.Loading
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

    fun getCachedForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCachedForecast()
                .catch { _ ->
                    // Ignore error for now since it's loaded on start up with dialog
                }.collect {
                    _forecast.value = ForecastState.ForecastSuccess(it)
                }
        }
    }

}

class ForecastViewModelFactory(private val repository: ForecastRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}