package com.anwera97.roomexample.data.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)