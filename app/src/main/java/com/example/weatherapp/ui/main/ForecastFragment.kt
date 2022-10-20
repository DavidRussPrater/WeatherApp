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
import com.example.weatherapp.ui.forecastdetails.ForecastDetailsFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ForecastFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastFragment()
    }

    private val viewModel: ForecastViewModel by viewModels {
        ForecastViewModelFactory((requireActivity().application as WeatherApp).repository)
    }

    private lateinit var binding: FragmentForecastBinding
    private val forecastAdapter: ForecastAdapter = ForecastAdapter()
    private var snackbar: Snackbar? = null

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
        viewModel.getCachedForecastList()
    }

    private fun observeForecastState() {
        lifecycleScope.launchWhenStarted {
            viewModel.forecast.collect { forecastState ->
                when (forecastState) {
                    is ForecastState.Loading -> {
                        binding.progressIndicator.bringToFront()
                        binding.progressIndicator.show()
                    }
                    is ForecastState.Failure -> {
                        showErrorMessage(
                            forecastState.msg.message
                                ?: getString(R.string.unknown_error)
                        )
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
                    is ForecastState.Empty -> {
                        showDialog()
                    }
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        snackbar = Snackbar.make(binding.main, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { showDialog() }
        snackbar?.run { show() }
    }

    private fun showDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(getString(R.string.dialog_title))

        val input = EditText(requireContext())
        input.hint = getString(R.string.dialog_hint)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton(getString(R.string.ok)) { _, _ ->
            val zip = input.text.toString()
            viewModel.getLocation(getString(R.string.zip_format, zip))
        }
        builder.show()
    }

    private fun setupDailyAdapter() {
        binding.dailyList.apply {
            adapter = forecastAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        forecastAdapter.onItemClick = { key ->
            openForecastDetails(key)
        }
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
    }

    private fun openForecastDetails(key: Long) {
        val detailsFragment = ForecastDetailsFragment.newInstance()
        val bundle = Bundle()
        bundle.apply {
            putLong("key", key)
        }
        detailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                detailsFragment,
                "DetailsFragment"
            )
            .addToBackStack(null)
            .commit()
    }

}