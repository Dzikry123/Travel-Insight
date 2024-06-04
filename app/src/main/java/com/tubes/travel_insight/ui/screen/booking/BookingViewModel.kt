package com.tubes.travel_insight.ui.screen.booking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tubes.travel_insight.data.BookingDao
import com.tubes.travel_insight.database.BookingDatabase
import com.tubes.travel_insight.model.Booking
import com.tubes.travel_insight.model.Tourism
import com.tubes.travel_insight.repository.TourismRepository
import com.tubes.travel_insight.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookingViewModel (private val repository: TourismRepository, application: Application) : AndroidViewModel(application) {

    private val _detailDataState: MutableStateFlow<UiState<Tourism>> =
        MutableStateFlow(UiState.Loading)
    val detailTourismState: StateFlow<UiState<Tourism>> get() = _detailDataState

    fun getDetailTourismById(id: Int) {
        viewModelScope.launch {
            _detailDataState.value = UiState.Loading
            _detailDataState.value = UiState.Success(repository.getTourismById(id))
        }
    }


    //db


    private val dao: BookingDao
    private val _allBookings = MutableLiveData<List<Booking>>()
    private val _getBooking = MutableLiveData<Booking>()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val allBookings: LiveData<List<Booking>> = _allBookings
    val getBooking: LiveData<Booking> = _getBooking

    init {
        dao = BookingDatabase.getInstance(application).bookingDao()
    }

    fun getBookings() {
        viewModelScope.launch(Dispatchers.IO) {
            _allBookings.postValue(dao.getAllBookings())
        }
    }

    fun getBooking(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getBooking.postValue(dao.getBooking(id))
        }
    }

    fun addBooking(travel: Booking) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.insertBooking(travel)}
        }
        getBookings()
    }

    fun deleteBooking(travel: Booking) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.deleteBooking(travel)}
        }
        getBookings()
    }


    fun updateBooking(id: Int, name: String, visitDate: String, notes: String, ticketPrice: String) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {
                dao.updateBooking(id, name, visitDate, notes, ticketPrice)
            }
        }
        getBookings()
    }

}