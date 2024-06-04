package com.tubes.travel_insight.ui.screen.booking

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.tubes.travel_insight.R
import com.tubes.travel_insight.model.Booking
import com.tubes.travel_insight.ui.common.HandleBackPress
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.theme.poppinsFamily


@Composable
fun AllBookingScreen(navController: NavController, bookingViewModel: BookingViewModel) {
    val bookings: List<Booking> by bookingViewModel.allBookings.observeAsState(initial = listOf())
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        bookingViewModel.getBookings()
    }

    LaunchedEffect(Unit) {
        bookingViewModel.getBookings()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F51FF)) // Blue color
                .padding(vertical = 24.dp, horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Travel Insight",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Booking List",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    fontFamily = poppinsFamily,

                    )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Booking List Title
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Your Booking List",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,

                )
            Divider(
                color = Color(0xFFFFA500), // Orange color
                thickness = 2.dp,
                modifier = Modifier.width(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Booking Items
        if (bookings.isEmpty()) {
            Column(
            ) {
                EmptyContentBook()
            }
        } else {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                bookings.forEach { booking ->
                    BookingItem(booking = booking, navController = navController, id= booking.id)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }


    if (backDispatcher != null) {
        HandleBackPress(backDispatcher) {
            navController.navigate(Screen.Home.route)
        }
    }


}

@Composable
fun BookingItem(booking: Booking, navController: NavController, id : Int ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable {
                navController.navigate(route = Screen.BookingDetailScreen.route + "/" + booking.id)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = booking.picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(21.dp))

            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = booking.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 0.dp, bottom = 2.dp),
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Visit Date : ${booking.visitDate}",
                    fontSize = 12.sp,
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Notes : ${booking.notes}",
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Price - ${booking.ticketPrice}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navController.navigate(route = Screen.AddReviewScreen.createRoute(booking.id))
                        Log.d("ID BOOK REVIEW clicked", "${booking.id}")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1F51FF)),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Review",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsFamily,
                        fontSize = 14.sp
                    )
                }
            }
        }


    }
}


@Composable
fun EmptyContentBook() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .offset(y = -10.dp),
            painter = painterResource(
                R.drawable.travel
            ), contentDescription = stringResource(
                R.string.no_travel_place
            ),
            tint = Color.LightGray
        )
        Text(
            text = stringResource(
                R.string.text_empty_content
            ),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.Gray
        )
    }
}
