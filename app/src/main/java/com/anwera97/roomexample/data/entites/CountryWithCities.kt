package com.anwera97.roomexample.data.entites

import androidx.room.Embedded
import androidx.room.Relation

data class CountryWithCities(
    @Embedded val country: Country,
    @Relation(
        parentColumn = "id",
        entityColumn = "country_id"
    )
    val cities: List<City>
)