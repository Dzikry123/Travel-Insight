package com.tubes.travel_insight.model

data class Tourism(
    val id: Int,
    val name: String,
    val location: String,
    val rate: String,
    val description: String,
    val ticketPrice: String,
    val picture: Int,
//    @ColumnInfo(name = "schedule")
//    val schedule: List<Schedule>,
    val isFavorite: Boolean
)