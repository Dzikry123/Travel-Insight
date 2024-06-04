package com.tubes.travel_insight.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tubes.travel_insight.model.Review

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: Review)

    @Query("SELECT * FROM review WHERE id = :id")
    fun getReview(id: Int): Review

    @Query("SELECT * FROM review")
    fun getAllReviews(): List<Review>

    @Delete
    fun deleteReview(review: Review)

    @Query("UPDATE review SET placeName = :placeName, content = :content, author = :author, rate = :rate WHERE id = :id")
    fun updateBooking(id: Int, placeName: String, content: String, author: String, rate: String)

}