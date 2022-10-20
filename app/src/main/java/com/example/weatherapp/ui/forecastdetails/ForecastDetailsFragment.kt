package com.example.weatherapp.ui.forecastdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.data.model.Daily
import com.example.weatherapp.databinding.FragmentForecastDetailsBinding
import com.google.android.material.snackbar.Snackbar

class ForecastDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastDetailsFragment()
    }

    private val viewModel: ForecastDetailsViewModel by viewModels {
        ForecastDetailsViewModelFactory((requireActivity().application as WeatherApp).repository)
    }

    private lateinit var binding: FragmentForecastDetailsBinding
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = savedInstanceState?.getLong("key")
            ?: requireArguments().getLong("key")
        observeForecast(key)
    }

    private fun observeForecast(key: Long) {
        lifecycleScope.launchWhenStarted {
            viewModel.forecast.collect { forecastState ->
                when (forecastState) {
                    ForecastDetailsState.Empty -> {
                        viewModel.getCachedForecast(key)
                    }
                    is ForecastDetailsState.Failure -> {
                        showErrorMessage(
                            forecastState.msg.message
                                ?: getString(R.string.unknown_error), key
                        )
                    }
                    is ForecastDetailsState.Success -> {
                        renderView(forecastState.daily)
                    }
                }
            }
        }
    }

    private fun renderView(daily: Daily) {
        binding.forecastDate.text = daily.date
        binding.description.text = daily.weather
        binding.tempMax.text = getString(R.string.temp_format, daily.tempMax)
        binding.tempMin.text = getString(R.string.temp_format, daily.tempMin)
        binding.windSpeed.text = getString(R.string.wind_format, daily.windSpeed)
        binding.windGust.text = getString(R.string.wind_format, daily.windGust)
        binding.windDirection.text = viewModel.convertWindDirection(daily.windDeg)
        binding.humidity.text = getString(R.string.percent_format, daily.humidity)
        binding.dewPoint.text = getString(R.string.temp_format, daily.dewPoint)
        binding.pressure.text = getString(R.string.pressure_format, daily.pressure)
        binding.cloudCover.text = getString(R.string.percent_format, daily.clouds)
        binding.precipitation.text = getString(R.string.percent_format, daily.pop)
        binding.uv.text = daily.uvi.toString()
    }

    private fun showErrorMessage(message: String, key: Long) {
        snackbar = Snackbar.make(binding.forecastDetailsFragment, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) {
                viewModel.getCachedForecast(key)
            }
        snackbar?.run { show() }
    }

}