package com.tubes.travel_insight.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "visitDate")
    val visitDate: String,
    @ColumnInfo(name = "notes")
    val notes: String,
    @ColumnInfo(name = "ticketPrice")
    val ticketPrice: String,
    @ColumnInfo(name = "picture")
    val picture: Int
)