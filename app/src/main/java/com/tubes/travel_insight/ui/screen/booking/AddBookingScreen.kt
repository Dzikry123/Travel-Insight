package com.tubes.travel_insight.ui.screen.booking

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
import androidx.compose.material.icons.filled.CalendarMonth
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.tubes.travel_insight.di.Injection
import com.tubes.travel_insight.model.Booking
import com.tubes.travel_insight.model.Tourism
import com.tubes.travel_insight.ui.ViewModelFactory
import com.tubes.travel_insight.ui.common.UiState
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.theme.poppinsFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookingScreen(
    bookingViewModel: BookingViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), application = Application())
    ),
    navController: NavController,
    id: Int
) {

    bookingViewModel.detailTourismState.collectAsState(initial = UiState.Loading).value.let { detailState ->
        when (detailState) {
            is UiState.Loading -> {
                bookingViewModel.getDetailTourismById(id)
            }

            is UiState.Success -> {
                BookingContent(
                    booking = detailState.data,
                    bookingViewModel = bookingViewModel,
                    navController = navController
                )
            }

            is UiState.Error -> {}
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BookingContent(
    booking: Tourism,
    bookingViewModel: BookingViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val bookingPlace = booking.name
    val imageTourism = booking.picture

    var notes by rememberSaveable { mutableStateOf("") }
    var visitDate by rememberSaveable { mutableStateOf("") }
    var ticketPrice by rememberSaveable { mutableStateOf("") }
    var isPriceDropDownExpanded by remember { mutableStateOf(false) }
    val priceList = listOf(
        "$20",
        "$30",
        "$40",
        "$55",
        "$67",
    )


    val calendarState = rememberUseCaseState()
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            Log.d("Selection Date ", " $date")
            visitDate = date.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )

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
                    text = "Howdy",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                   fontFamily = poppinsFamily,

                   fontSize = 30.sp
                )
               Text(
                    text = "Let's book your Place",
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

        // Booking Place
        Text(
            text = "Booking Place",
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
            value = bookingPlace,
            label = { Text(stringResource(id = R.string.place_name)) },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notes
        OutlinedTextField(
            value = notes,
            label = { Text(stringResource(id = R.string.notes)) },
            onValueChange = {
                notes = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Visit Date
        OutlinedTextField(
            value = visitDate,
            label = { Text(stringResource(id = R.string.visit_date)) },
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 16.sp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.clickable { calendarState.show() }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ticket Price
        Box {
            OutlinedTextField(
                value = ticketPrice,
                onValueChange = { ticketPrice = it },
                placeholder = { Text(text = "Choose The Ticket Price", color = Color.DarkGray) },
                enabled = false,
                modifier = Modifier
                    .clickable {
                        isPriceDropDownExpanded = true
                    }
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)) ,
                textStyle = TextStyle(color = Color.Black),
                trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
            )

            DropdownMenu(
                expanded = isPriceDropDownExpanded,
                onDismissRequest = { isPriceDropDownExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
            ) {
                priceList.forEachIndexed { index, selectedItem ->
                    DropdownMenuItem(onClick = {
                        ticketPrice = selectedItem
                        isPriceDropDownExpanded = false
                    }) {
                        Text(selectedItem)
                    }
                    if (index != priceList.lastIndex)
                        Divider(Modifier.background(Color.Black))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Create the travel object
                if (bookingPlace.isEmpty() || visitDate.isEmpty() || notes.isEmpty() || ticketPrice.isEmpty()) {
                    Toast.makeText(context, "Booking Added Failed \n Please Fill in All Fields", Toast.LENGTH_SHORT).show()
                    Log.d("data db", "Data Gagal")
                } else {
                    val bookingPlace = Booking(
                        name = bookingPlace,
                        visitDate = visitDate,
                        notes = notes,
                        ticketPrice = ticketPrice,
                        picture = imageTourism
                    )
                    Log.d("data db", "Data Berhasil $bookingPlace")

                    // Update the booking to the database
                    bookingViewModel.addBooking(bookingPlace)
                    notes = ""
                    visitDate = ""
                    ticketPrice = ""
                    Toast.makeText(context, "Booking added successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Screen.AllBookingScreen.route)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1F51FF)),
            modifier = Modifier.fillMaxWidth(),
            ) {
            Text(
                text = "Add Booking Place",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )


        }

    }


}