package com.tubes.travel_insight.ui.screen.booking

import android.util.Log
import com.tubes.travel_insight.R
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.tubes.travel_insight.model.Booking
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(id: Int, bookingViewModel: BookingViewModel, navController: NavController) {
    val context = LocalContext.current
    var bookingPlaceState: String? by remember { mutableStateOf(null) }
    var notes: String? by remember { mutableStateOf(null) }
    var visitDate: String? by remember { mutableStateOf(null) }
    var ticketPrice: String? by remember { mutableStateOf(null) }
    var isPriceDropDownExpanded by remember { mutableStateOf(false) }
    val priceList = listOf(
        "$20",
        "$30",
        "$40",
        "$55",
        "$67",
    )


    LaunchedEffect(Unit) {
        bookingViewModel.getBooking(id)
    }
    bookingViewModel.getBooking(id)

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


    val booking = bookingViewModel.getBooking.observeAsState().value
    booking ?: return
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
            value = bookingPlaceState ?: booking.name,
            label = { Text(stringResource(id = R.string.place_name)) },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notes
        OutlinedTextField(
            value = notes ?: booking.notes,
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
            value = visitDate ?: booking.visitDate,
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
                value = ticketPrice ?: booking.ticketPrice,
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
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                // Create the travel object
                val booking = Booking(
                    name = bookingPlaceState ?: booking.name,
                    visitDate = visitDate ?: booking.visitDate,
                    notes = notes ?: booking.notes,
                    ticketPrice = ticketPrice ?: booking.ticketPrice,
                    picture = booking.picture
                )

                // Update the travel in the database
                bookingViewModel.updateBooking(
                    id,
                    booking.name,
                    booking.visitDate,
                    booking.notes,
                    booking.ticketPrice
                )
                Toast.makeText(context, "Booking updated successfully", Toast.LENGTH_SHORT)
                    .show()
            },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1F51FF)
                ),
                modifier = Modifier.fillMaxWidth(),

                )
            {
                Text(text = "Update Booking")
            }
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                val openDialog = remember { mutableStateOf(false) }

                Button(
                    onClick = { openDialog.value = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text(text = "Delete Booking")
                }

                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = {
                            Text(text = "Deleting Booking")
                        },
                        text = {
                            Text(text = "Do you really want to Delete this Booking ?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    booking?.let { id ->
                                        bookingViewModel.deleteBooking(id)
                                    }
                                    openDialog.value = false
                                    Toast.makeText(context, "Booking Deleted successfully", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.navigate(Screen.AllBookingScreen.route)
                                },
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1F51FF)
                                )
                            ) {
                                Text(text = "CONFIRM")

                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { openDialog.value = false },
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1F51FF)
                                )
                            ) {
                                Text(text = "CANCEL")
                            }
                        }
                    )
                }
            }

        }


    }

}





