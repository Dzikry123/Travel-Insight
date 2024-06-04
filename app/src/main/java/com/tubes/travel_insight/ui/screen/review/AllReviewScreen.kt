package com.tubes.travel_insight.ui.screen.review

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
import com.tubes.travel_insight.model.Review
import com.tubes.travel_insight.ui.common.HandleBackPress
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.theme.poppinsFamily


@Composable
fun AllReviewScreen(navController: NavController, reviewViewModel: ReviewViewModel) {
    val reviews: List<Review> by reviewViewModel.allReviews.observeAsState(initial = listOf())
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        reviewViewModel.getReviews()
    }

    LaunchedEffect(Unit) {
        reviewViewModel.getReviews()
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
                    text = "Review List",
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
                text = "Your Reviews List",
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
        if (reviews.isEmpty()) {
            Column(
            ) {
                EmptyContent()
            }
        } else {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                reviews.forEach { review ->
                    ReviewItem(review = review, navController = navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }



        if (backDispatcher != null) {
            HandleBackPress(backDispatcher) {
                navController.navigate(Screen.Home.route)
            }
        }

    }



}

@Composable
fun ReviewItem(review: Review, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable {
                navController.navigate(route = Screen.ReviewDetailScreen.route + "/" + review.id
                )
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = review.picture),
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
                    text = review.placeName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 0.dp, bottom = 2.dp),
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Author : ${review.author}",
                    fontSize = 12.sp,
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Content : ${review.content}",
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,

                    )
                Text(
                    text = "Rate - ${review.rate}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navController.navigate(
                            route = Screen.ReviewDetailScreen.route + "/" + review.id
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1F51FF)),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Detail",
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
fun EmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(
                R.drawable.review
            ), contentDescription = stringResource(
                R.string.no_travel_place
            ),
            tint = Color.LightGray
        )
        androidx.compose.material.Text(
            text = stringResource(
                R.string.text_empty_review
            ),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.LightGray
        )
    }
}
