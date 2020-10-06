package com.anwera97.roomexample.presentation.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anwera97.roomexample.R
import com.anwera97.roomexample.data.entites.City
import com.anwera97.roomexample.databinding.ItemLocationBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    var cities: List<City> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = field.size

                override fun getNewListSize(): Int = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition] == value[newItemPosition]
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return field[oldItemPosition].id == value[newItemPosition].id
                }

            }).also {
                field = value
                it.dispatchUpdatesTo(this)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_location,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(cities[position])
    }

    override fun getItemCount() = cities.size

    class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(city: City) {
            binding.title = city.name
            binding.subtitle = "Población de ${city.population}"
            binding.onClick = false
        }
    }
}