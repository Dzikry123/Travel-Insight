package com.tubes.travel_insight.ui.screen.review

import android.app.Application
import androidx.compose.runtime.Composable
import com.tubes.travel_insight.R

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tubes.travel_insight.di.Injection
import com.tubes.travel_insight.model.Booking
import com.tubes.travel_insight.model.Review
import com.tubes.travel_insight.ui.ViewModelFactory
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.screen.booking.BookingViewModel
import com.tubes.travel_insight.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    reviewViewModel: ReviewViewModel,
    bookingViewModel: BookingViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), application = Application())
    ),
    navController: NavController,
    id: Int
) {
    LaunchedEffect(Unit) {
        bookingViewModel.getBooking(id)
    }
    bookingViewModel.getBooking(id)
    val booking = bookingViewModel.getBooking.observeAsState().value
    booking ?: return
    ReviewContent(
                    review = booking,
                    reviewViewModel = reviewViewModel,
                    navController = navController,
                    id = id
                )



}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ReviewContent(
    review: Booking,
    reviewViewModel: ReviewViewModel,
    navController: NavController,
    id: Int
) {

    val context = LocalContext.current
    val placeName = review.name
    val imageTourism = review.picture
    var content by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    var rate by rememberSaveable { mutableStateOf("") }
    var isRateDropDownExpanded by remember { mutableStateOf(false) }
    val rateList = listOf("Very Bad ", "Bad", "Good", "Great", "Very Great")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1F51FF)) // Blue color
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Travel Insight",
                    color = Color.White,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,


                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "Review",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFamily,

                    fontSize = 30.sp
                )
                Text(
                    text = "Please Give Your Feedback",
                    color = Color.White,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,

                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(60.dp))

            Image(
                painter = painterResource(id = R.drawable.profile_new), // Replace with your own icon
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Review Place
        Text(
            text = "Review Place",
            fontFamily = poppinsFamily,

            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Divider(
            color = Color(0xFFFFA500), // Orange color
            thickness = 2.dp,
            modifier = Modifier.width(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Place Name
        OutlinedTextField(
            value = review.name,
            label = { Text(stringResource(id = R.string.place_name)) },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notes
        OutlinedTextField(
            value = content,
            label = { Text(stringResource(id = R.string.content)) },
            onValueChange = {
                content = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Visit Date
        OutlinedTextField(
            value = author,
            label = { Text(stringResource(id = R.string.author)) },
            onValueChange = {
                author = it
            },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ticket Price
        Box {
            OutlinedTextField(
                value = rate,
                onValueChange = { rate = it },
                placeholder = { Text(text = "Choose The Rating", color = Color.DarkGray) },
                enabled = false,
                modifier = Modifier
                    .clickable {
                        isRateDropDownExpanded = true
                    }
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
                textStyle = TextStyle(color = Color.Black),
                trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
            )

            DropdownMenu(
                expanded = isRateDropDownExpanded,
                onDismissRequest = { isRateDropDownExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
            ) {
                rateList.forEachIndexed { index, selectedItem ->
                    DropdownMenuItem(onClick = {
                        rate = selectedItem
                        isRateDropDownExpanded = false
                    }) {
                        Text(selectedItem)
                    }
                    if (index != rateList.lastIndex)
                        Divider(Modifier.background(Color.Black))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Create the travel object
                if (placeName.isEmpty() || content.isEmpty() || author.isEmpty() || rate.isEmpty()) {
                    Toast.makeText(context, "Review Added Failed \n Please Fill in All Fields", Toast.LENGTH_SHORT).show()
                    Log.d("data db", "Data Gagal")
                } else {
                    val reviewPlace = Review(
                        placeName = placeName,
                        content = content,
                        author = author,
                        rate = rate,
                        picture = imageTourism
                    )
                    Log.d("data db", "Data Berhasil $reviewPlace")

                    // Update the booking to the database
                    reviewViewModel.addReview(reviewPlace)
                    content = ""
                    author = ""
                    rate = ""
                    Toast.makeText(context, "Review added successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Screen.AllReviewScreen.route)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1F51FF)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Add Review Place",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )


        }

    }


}


