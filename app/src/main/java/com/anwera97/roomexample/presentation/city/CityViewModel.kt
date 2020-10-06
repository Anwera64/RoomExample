package com.anwera97.roomexample.presentation.city

import android.app.Application
import androidx.lifecycle.*
import com.anwera97.roomexample.data.AppDatabase
import com.anwera97.roomexample.data.entites.City
import com.anwera97.roomexample.data.entites.Country
import com.anwera97.roomexample.data.entites.CountryWithCities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class CityViewModel(app: Application) : AndroidViewModel(app) {

    private val database: AppDatabase = AppDatabase.getDatabase(app)

    fun getCities(countryId: String): LiveData<CountryWithCities> {
        return database.cityDao().getByCountry(countryId)
    }

    fun createRandomCity(countryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = UUID.randomUUID().toString()
            database.cityDao().insertAll(City(id, id, Random.nextInt(), countryId))
        }
    }

    fun deleteCountry(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            database.cityDao().delete(city)
        }
    }
}