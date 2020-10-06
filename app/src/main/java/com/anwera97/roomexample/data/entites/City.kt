package com.anwera97.roomexample.data.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Country::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("country_id"),
    onDelete = ForeignKey.CASCADE)]
)
data class City(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "population") val population: Int,
    @ColumnInfo(name = "country_id") val countryId: String
)