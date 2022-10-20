package com.example.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Daily
import com.example.weatherapp.databinding.ForecastLineItemBinding

class ForecastAdapter :
    ListAdapter<Daily, ForecastAdapter.ForecastViewHolder>(DailyItemDiffCallback()) {

    var onItemClick: (key: Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ForecastLineItemBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ForecastViewHolder(
        private val binding: ForecastLineItemBinding,
        private val onItemClick: (key: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily) = with(binding) {
            date.text = daily.date
            temp.text = binding.root.context.getString(R.string.temp_format, daily.tempMin)
            wind.text = daily.windSpeed.toString()
            description.text = daily.weather
            root.setOnClickListener {
                onItemClick.invoke(daily.time)
            }
        }
    }
}

class DailyItemDiffCallback : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean =
        oldItem == newItem
}