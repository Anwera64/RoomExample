package com.anwera97.roomexample

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anwera97.roomexample.data.AppDatabase
import com.anwera97.roomexample.data.dao.CityDao
import com.anwera97.roomexample.data.dao.CountryDao
import com.anwera97.roomexample.data.entites.City
import com.anwera97.roomexample.data.entites.Country
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var countryDao: CountryDao
    private lateinit var cityDao: CityDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        countryDao = db.countryDao()
        cityDao = db.cityDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCountryAndReadInList() = runBlocking {
        val name = "Pais de prueba"
        val country = Country(UUID.randomUUID().toString(), name)
        countryDao.insertAll(country)
        val byName = countryDao.findByName(name)
        assertThat(byName[0], equalTo(country))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAndCheck() = runBlocking {
        val name = "Pais de prueba"
        val country = Country(UUID.randomUUID().toString(), name)
        countryDao.insertAll(country)
        countryDao.delete(country)
        val shouldBeEmpty = countryDao.findByName(name)
        assertThat(shouldBeEmpty.isEmpty(), equalTo(true))
    }

    @Test
    @Throws(Exception::class)
    fun createCountryWithCitiesAndCheck() = runBlocking {
        val countryName = "Pais de prueba"
        val cityName = "Ciudad de prueba"
        val country = Country(UUID.randomUUID().toString(), countryName)
        countryDao.insertAll(country)
        val city1 = City(UUID.randomUUID().toString(), cityName, Math.random().toInt(), country.id)
        val city2 = City(UUID.randomUUID().toString(), cityName, Math.random().toInt(), country.id)
        val city3 = City(UUID.randomUUID().toString(), cityName, Math.random().toInt(), country.id)
        cityDao.insertAll(city1, city2, city3)
        val countryWithCities = cityDao.getByCountryTest(country.id)
        assertThat(countryWithCities.cities.isEmpty(), equalTo(false))
        assertThat(countryWithCities.country, equalTo(country))
    }
}