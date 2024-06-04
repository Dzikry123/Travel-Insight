package com.tubes.travel_insight.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AboutMe : Screen("about_me")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    object AddBookingScreen : Screen("add_a_booking_trip/{id}") {
        fun createRoute(id: Int) = "add_a_booking_trip/$id"
    }
    object BookingDetailScreen : Screen("booking_detail/{id}") {
        fun createRoute(id: Int) = "booking_detail/$id"
    }

    object AddReviewScreen : Screen("add_a_review_trip/{id}") {
        fun createRoute(id: Int) = "add_a_review_trip/$id"
    }

    object ReviewDetailScreen : Screen("review_detail/{id}") {
        fun createRoute(id: Int) = "review_detail/$id"
    }

    object AllBookingScreen : Screen("all_bookings")
    object AllReviewScreen : Screen("review_list")
}