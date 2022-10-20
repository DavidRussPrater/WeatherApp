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
                    val a = key
                    _forecast.value = ForecastDetailsState.Success(it)
                }
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