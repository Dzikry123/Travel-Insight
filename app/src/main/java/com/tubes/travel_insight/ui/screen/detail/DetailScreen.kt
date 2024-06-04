package com.tubes.travel_insight.ui.screen.detail

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tubes.travel_insight.R
import com.tubes.travel_insight.di.Injection
import com.tubes.travel_insight.model.Tourism
import com.tubes.travel_insight.ui.ViewModelFactory
import com.tubes.travel_insight.ui.common.BottomRoundedShape
import com.tubes.travel_insight.ui.common.UiState
import com.tubes.travel_insight.ui.components.CircleButton
import com.tubes.travel_insight.ui.components.DialogScreen
import com.tubes.travel_insight.ui.navigation.Screen
import com.tubes.travel_insight.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    id: Int,
    application : Application,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), application)
    ),
    navigateBack: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        viewModel.detailState.collectAsState(initial = UiState.Loading).value.let { detailState ->
            when (detailState) {
                is UiState.Loading -> {
                    viewModel.getDetailById(id)
                }
                is UiState.Success -> {
                    DetailHeader(
                        modifier = modifier,
                        navigateBack = navigateBack,
                        tourism = detailState.data,
                        isFavorite = viewModel.favoriteState,
                        favoriteClick = {
                            val result = viewModel.updateFavoriteTourism(id)

                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = result
                                )
                            }
                        }
                    )
                    DetailContent(modifier = modifier, tourism = detailState.data)
                    DetailBookingNow(
                        modifier = modifier,
                    )
                    DetailPriceAndContinue(modifier = modifier, navController = navController, id = id)
                    DialogScreen(
                        modifier = modifier,
                        dialogState = viewModel.dialogState,
                        onDismissRequest = {
                            viewModel.dialogState = false
                        })
                }
                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun DetailContent(modifier: Modifier, tourism: Tourism) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = tourism.name,
            color = BlackColor500,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            fontFamily = poppinsFamily,
            modifier = modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = tourism.location,
            color = BlackColor500,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 26.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "About",
            color = BlackColor500,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
        Text(
            text = tourism.description,
            color = BlackColorBody,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Light,
            maxLines = 4,
            lineHeight = 26.sp,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
    }
}

@Composable
fun DetailBookingNow(
    modifier: Modifier,
) {
    Text(
        text = "Booking Now",
        color = BlackColor500, fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 12.dp, start = 24.dp)
    )

}

@Composable
fun DetailPriceAndContinue(modifier: Modifier,  navController: NavHostController, id: Int) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 30.dp),
    ) {
        Column(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(end = 30.dp)
        ) {
            Text(
                text = "$22,800",
                color = BlueMain, fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
            )
            Text(
                text = "/person",
                color = BlackColor500, fontFamily = poppinsFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
            )
        }
        Button(
            onClick = { navController.navigate(route = Screen.AddBookingScreen.createRoute(id)) },
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BlueMain,
                contentColor = WhiteColor
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Book Now",
                color = WhiteColor, fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun DetailHeader(
    modifier: Modifier,
    tourism: Tourism,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    favoriteClick: () -> Unit,
) {
    Box {
        Image(
            painter = painterResource(id = tourism.picture),
            contentDescription = tourism.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(305.dp)
                .clip(shape = BottomRoundedShape())
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            CircleButton(
                modifier = modifier,
                onClick = navigateBack,
                icon = R.drawable.ic_arrow,
                iconDescription = R.string.back_icon_info
            )
            CircleButton(
                onClick = favoriteClick,
                icon = if (isFavorite) R.drawable.ic_heart_active else R.drawable.ic_heart,
                iconDescription = R.string.heart_icon_info
            )
        }
    }
}

//@Preview
//@Composable
//fun DetailScreenPreview() {
//    TourismAppTheme {
//        Scaffold() {
//        }
//    }
//}