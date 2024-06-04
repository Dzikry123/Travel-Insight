package com.tubes.travel_insight.repository

import com.tubes.travel_insight.model.Tourism
import kotlinx.coroutines.flow.Flow

interface TourismRepository {
    fun getAllTourism(): Flow<List<Tourism>>
    fun getTourismById(id: Int): Tourism
    fun updateFavoriteTourism(id: Int): String
}