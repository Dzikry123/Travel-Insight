package com.tubes.travel_insight.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tubes.travel_insight.repository.TourismRepository
import com.tubes.travel_insight.ui.screen.booking.BookingViewModel
import com.tubes.travel_insight.ui.screen.detail.DetailViewModel
import com.tubes.travel_insight.ui.screen.home.HomeViewModel
import com.tubes.travel_insight.ui.screen.review.ReviewViewModel

class ViewModelFactory(private val repository: TourismRepository, private val application: Application,) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
            return BookingViewModel(repository, application) as T
        } else if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}