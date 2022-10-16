package com.example.weatherapp.ui.main

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.databinding.ForecastLineItemBinding
import java.util.Locale

class ForecastAdapter :
    ListAdapter<Forecast.Daily, ForecastAdapter.ForecastViewHolder>(ForecastItemDiffCallback()) {

    val sdf = SimpleDateFormat("MMMM d, y", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ForecastLineItemBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ForecastViewHolder(
        private val binding: ForecastLineItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Forecast.Daily) = with(binding) {
            date.text = sdf.format(daily.dt * 1000L)
            temp.text = binding.root.context.getString(R.string.temp_format, daily.temp)
            wind.text = daily.windSpeed.toString()
            description.text = daily.weather.first().description
        }
    }
}

class ForecastItemDiffCallback : DiffUtil.ItemCallback<Forecast.Daily>() {
    override fun areItemsTheSame(oldItem: Forecast.Daily, newItem: Forecast.Daily): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Forecast.Daily, newItem: Forecast.Daily): Boolean =
        oldItem == newItem
}