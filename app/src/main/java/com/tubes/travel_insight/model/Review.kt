package com.tubes.travel_insight.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "placeName")
    val placeName: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "rate")
    val rate: String,

    @ColumnInfo(name = "picture")
    val picture: Int,
)