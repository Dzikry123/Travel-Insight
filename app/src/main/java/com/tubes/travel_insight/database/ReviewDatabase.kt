package com.tubes.travel_insight.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tubes.travel_insight.data.ReviewDao
import com.tubes.travel_insight.model.Review

@Database(
    entities = [Review::class],
    version = 1
)
abstract class ReviewDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        fun getInstance(context: Context): ReviewDatabase {
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java,
                    "review_db"
                ).build()
                INSTANCE = instance
                instance
            }
            return INSTANCE as ReviewDatabase
        }
    }
}