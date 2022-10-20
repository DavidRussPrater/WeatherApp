package com.example.weatherapp.ui.forecastdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ForecastDetailsViewModel(private val repository: ForecastRepository) : ViewModel() {

    private val _forecast = MutableStateFlow<ForecastDetailsState>(ForecastDetailsState.Empty)
    val forecast: StateFlow<ForecastDetailsState> = _forecast

    fun getCachedForecast(key: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCachedForecast(key)
                .catch { error ->
                    _forecast.value = ForecastDetailsState.Failure(error)
                }.collect {
                    _forecast.value = ForecastDetailsState.Success(it)
                }
        }
    }

    fun convertWindDirection(degree: Int): String {
        return when (degree) {
            in 338..359, in 0..23 -> { "N" }
            in 23..68 -> { "NE" }
            in 68..113 -> { "E" }
            in 113..158 -> { "SE" }
            in 158..203 -> { "S" }
            in 203..248 -> { "SW" }
            in 248..293 -> { "W" }
            else -> { "NW" }
        }
    }

}

class ForecastDetailsViewModelFactory(private val repository: ForecastRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForecastDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}