package com.anwera97.roomexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anwera97.roomexample.data.entites.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun getAll(): LiveData<List<Country>>

    @Query("SELECT * FROM country WHERE name = :name")
    fun findByName(name: String): List<Country>

    @Insert
    suspend fun insertAll(vararg countries: Country)

    @Delete
    suspend fun delete(user: Country)
}