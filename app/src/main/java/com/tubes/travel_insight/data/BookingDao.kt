package com.tubes.travel_insight.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tubes.travel_insight.model.Booking

@Dao
interface BookingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooking(booking: Booking)

    @Query("SELECT * FROM booking WHERE id = :id")
    fun getBooking(id: Int): Booking

    @Query("SELECT * FROM booking")
    fun getAllBookings(): List<Booking>

    @Delete
    fun deleteBooking(booking: Booking)

    @Query("UPDATE booking SET name = :name, visitDate = :visitDate, notes = :notes, ticketPrice = :ticketPrice WHERE id = :id")
    fun updateBooking(id: Int, name: String, visitDate: String, notes: String, ticketPrice: String)

}