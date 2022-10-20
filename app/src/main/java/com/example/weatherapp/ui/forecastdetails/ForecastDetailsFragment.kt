package com.example.weatherapp.ui.forecastdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentForecastDetailsBinding

class ForecastDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastDetailsFragment()
    }

    private lateinit var binding: FragmentForecastDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}