package com.example.weatherapp.ui.main

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ForecastFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastFragment()
    }

    private val forecastAdapter: ForecastAdapter = ForecastAdapter()

    private val viewModel: ForecastViewModel by viewModels {
        ForecastViewModelFactory((requireActivity().application as WeatherApp).repository)
    }
    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDailyAdapter()
        observeForecastState()
        showDialog()
    }

    private fun observeForecastState() {
        lifecycleScope.launchWhenStarted {
            viewModel.forecast.collect { forecastState ->
                when (forecastState) {
                    is ForecastState.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                    }
                    is ForecastState.Failure -> {
                        showErrorMessage(forecastState.msg.message!!)
                        binding.progressIndicator.hide()
                    }
                    is ForecastState.ForecastSuccess -> {
                        forecastAdapter.submitList(forecastState.dailyList)
                        binding.progressIndicator.hide()
                    }
                    is ForecastState.LocationSuccess -> {
                        viewModel.getForecast(
                            forecastState.location.lat.toString(),
                            forecastState.location.lon.toString()
                        )
                    }
                    is ForecastState.Empty -> {}
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { viewModel.getForecast(viewModel.latitude, viewModel.longitude) }
            .show()
    }

    private fun showDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Title")

        val input = EditText(requireContext())
        input.setHint("Enter Text")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { _, _ ->
            val zip = input.text.toString()
            viewModel.getLocation(requireContext().getString(R.string.zip_formated, zip))
        }
        builder.show()
    }

    private fun setupDailyAdapter() {
        binding.dailyList.apply {
            adapter = forecastAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}