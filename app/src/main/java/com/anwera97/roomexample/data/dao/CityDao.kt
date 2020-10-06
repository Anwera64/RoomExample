package com.anwera97.roomexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anwera97.roomexample.data.entites.City
import com.anwera97.roomexample.data.entites.CountryWithCities

@Dao
interface CityDao {
    @Transaction
    @Query("SELECT * FROM country WHERE id = :countryId")
    fun getByCountry(countryId: String): LiveData<CountryWithCities>

    @Insert
    suspend fun insertAll(vararg users: City)

    @Delete
    suspend fun delete(user: City)
}