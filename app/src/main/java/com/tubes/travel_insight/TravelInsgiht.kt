package com.tubes.travel_insight

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tubes.travel_insight.di.Injection
import com.tubes.travel_insight.ui.ViewModelFactory
import com.tubes.travel_insight.ui.navigation.BottomNavBar
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.screen.about_me.AboutMeScreen
import com.tubes.travel_insight.ui.screen.booking.AddBookingScreen
import com.tubes.travel_insight.ui.screen.booking.AllBookingScreen
import com.tubes.travel_insight.ui.screen.detail.DetailScreen
import com.tubes.travel_insight.ui.screen.home.HomeScreen
import com.tubes.travel_insight.ui.screen.booking.BookingViewModel
import com.tubes.travel_insight.ui.screen.booking.BookingDetailScreen
import com.tubes.travel_insight.ui.screen.review.AddReviewScreen
import com.tubes.travel_insight.ui.screen.review.AllReviewScreen
import com.tubes.travel_insight.ui.screen.review.ReviewDetailScreen
import com.tubes.travel_insight.ui.screen.review.ReviewViewModel

@Composable
fun TravelInsgiht(
    application: Application,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bookingViewModel: BookingViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), application)
    )


    val reviewViewModel: ReviewViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), application)
    )


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            if (currentRoute != null && !(
                        currentRoute.startsWith(Screen.Detail.route.substringBefore("/{id}")) ||
                                currentRoute.startsWith(Screen.AddBookingScreen.route.substringBefore("/{id}")) ||
                                currentRoute.startsWith(Screen.BookingDetailScreen.route.substringBefore("/{booking_detail}")) ||
                                currentRoute.startsWith(Screen.AddReviewScreen.route.substringBefore("/{id}")) ||
                                currentRoute.startsWith(Screen.ReviewDetailScreen.route.substringBefore("/{review_detail}"))
                        )) {
                BottomNavBar(navController)
            }
        },

        ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    navigateToAboutMe = {
                        navController.navigate(Screen.AboutMe.route)
                    }
                )
            }
            composable(Screen.AboutMe.route) {
                AboutMeScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                DetailScreen(
                    id = id,
                    application = application,
                    scaffoldState = scaffoldState,
                    scope = scope,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navController = navController
                )
            }
            composable(
                route = Screen.AddBookingScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                AddBookingScreen(
                    bookingViewModel = bookingViewModel,
                    id = id,
                    navController = navController
                )
            }
            composable(
                route = Screen.AllBookingScreen.route,
            ) {
                AllBookingScreen(navController = navController, bookingViewModel = bookingViewModel)
            }
            composable(
                route = Screen.BookingDetailScreen.route + "/{booking_detail}",
                arguments = listOf(
                    navArgument("booking_detail") {
                        type = NavType.IntType
                        defaultValue = -1
                        nullable = false
                    }
                )
            ) {
                val id = it.arguments?.getInt("booking_detail") ?: -1
                BookingDetailScreen(id, bookingViewModel = bookingViewModel, navController)
            }
            composable(
                route = Screen.AddReviewScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                AddReviewScreen(
                    reviewViewModel = reviewViewModel,
                    bookingViewModel = bookingViewModel,
                    id = id,
                    navController = navController
                )
            }

            composable(
                route = Screen.AllReviewScreen.route,
            ) {
                AllReviewScreen(navController = navController, reviewViewModel = reviewViewModel)
            }
            composable(
                route = Screen.ReviewDetailScreen.route + "/{booking_detail}",
                arguments = listOf(
                    navArgument("booking_detail") {
                        type = NavType.IntType
                        defaultValue = -1
                        nullable = false
                    }
                )
            ) {
                val id = it.arguments?.getInt("booking_detail") ?: -1
                ReviewDetailScreen(id, reviewViewModel = reviewViewModel, navController)
            }

        }
    }
}