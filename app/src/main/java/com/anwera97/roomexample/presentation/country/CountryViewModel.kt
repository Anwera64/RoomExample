package com.anwera97.roomexample.presentation.country

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.anwera97.roomexample.data.AppDatabase
import com.anwera97.roomexample.data.entites.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(app: Application) : AndroidViewModel(app) {

    private val database: AppDatabase
    val countries: LiveData<List<Country>>
    private var countrySet = Stack<String>().apply {
        add("Peru")
        add("Rusia")
        add("EEUU")
        add("Francia")
        add("Prussia")
        add("Slovakia")
        add("Bolivia")
        add("Chile")
        add("China")
    }

    init {
        database = AppDatabase.getDatabase(app)
        countries = database.countryDao().getAll()
    }

    fun createRandomCountry() {
        if (countrySet.isEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            database.countryDao().insertAll(Country(UUID.randomUUID().toString(), countrySet.pop()))
        }
    }

    fun deleteCountry(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            database.countryDao().delete(country)
        }
    }
}