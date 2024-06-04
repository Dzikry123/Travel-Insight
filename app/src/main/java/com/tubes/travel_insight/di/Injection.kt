package com.tubes.travel_insight.di

import com.tubes.travel_insight.repository.TourismRepository
import com.tubes.travel_insight.repository.TourismRepositoryImpl

object Injection {
    fun provideRepository(): TourismRepository {
        return TourismRepositoryImpl.getInstance()
    }
}