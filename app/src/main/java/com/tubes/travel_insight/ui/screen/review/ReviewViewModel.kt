package com.tubes.travel_insight.ui.screen.review

import com.tubes.travel_insight.data.ReviewDao
import com.tubes.travel_insight.database.ReviewDatabase
import com.tubes.travel_insight.model.Review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tubes.travel_insight.model.Tourism
import com.tubes.travel_insight.repository.TourismRepository
import com.tubes.travel_insight.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReviewViewModel (private val repository: TourismRepository, application: Application) : AndroidViewModel(application) {

    private val _detailDataState: MutableStateFlow<UiState<Tourism>> =
        MutableStateFlow(UiState.Loading)
    val detailTourismState: StateFlow<UiState<Tourism>> get() = _detailDataState

    fun getDetailTourismById(id: Int) {
        viewModelScope.launch {
            _detailDataState.value = UiState.Loading
            _detailDataState.value = UiState.Success(repository.getTourismById(id))
        }
    }


    private val dao: ReviewDao
    private val _allReviews = MutableLiveData<List<Review>>()
    private val _getReview = MutableLiveData<Review>()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val allReviews: LiveData<List<Review>> = _allReviews
    val getReview: LiveData<Review> = _getReview

    init {
        dao = ReviewDatabase.getInstance(application).reviewDao()
    }

    fun getReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _allReviews.postValue(dao.getAllReviews())
        }
    }

    fun getReview(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getReview.postValue(dao.getReview(id))
        }
    }

    fun addReview(travel: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.insertReview(travel)}
        }
        getReviews()
    }

    fun deleteReview(travel: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.deleteReview(travel)}
        }
        getReviews()
    }

    fun updateReview(id: Int, placeName: String, author : String, content: String, rate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {
                dao.updateBooking(id, placeName, author, content, rate)
            }
        }
        getReviews()
    }
}