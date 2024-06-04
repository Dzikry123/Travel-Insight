package com.tubes.travel_insight.database

import com.tubes.travel_insight.data.BookingDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tubes.travel_insight.model.Booking

@Database(
    entities = [Booking::class],
    version = 1
)
abstract class BookingDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getInstance(context: Context): BookingDatabase {
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookingDatabase::class.java,
                    "booking_db"
                ).build()
                INSTANCE = instance
                instance
            }
            return INSTANCE as BookingDatabase
        }
    }
}