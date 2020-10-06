package com.anwera97.roomexample.presentation.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anwera97.roomexample.R
import com.anwera97.roomexample.data.entites.Country
import com.anwera97.roomexample.databinding.ItemLocationBinding

class CountryAdapter(private val interactor: CountryInteractor) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    var countries: List<Country> = emptyList()
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
        return ViewHolder(binding, interactor)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(countries[position])
    }

    override fun getItemCount() = countries.size

    class ViewHolder(
        private val binding: ItemLocationBinding,
        private val interactor: CountryInteractor
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(country: Country) {
            binding.title = country.name
            binding.onClick = true

            binding.clItem.setOnClickListener { interactor.onClick(country.id) }
        }
    }

    interface CountryInteractor {
        fun onClick(id: String)
    }
}