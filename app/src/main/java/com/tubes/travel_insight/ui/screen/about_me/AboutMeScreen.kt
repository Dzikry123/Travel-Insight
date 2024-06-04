package com.tubes.travel_insight.ui.screen.about_me

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tubes.travel_insight.ui.components.CustomTopAppBar
import com.tubes.travel_insight.ui.theme.BlackColor500
import com.tubes.travel_insight.ui.theme.GreyColor300
import com.tubes.travel_insight.ui.theme.poppinsFamily
import com.tubes.travel_insight.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutMeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                elevation = 0.dp,
                icon = Icons.Filled.ArrowBack,
                iconDescription = stringResource(id = R.string.back_icon_info),
                onIconClick = navigateBack,
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.profile_new),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .width(150.dp)
                    .padding(bottom = 16.dp, top = 16.dp)
                    .clip(shape = CircleShape),
                contentDescription = stringResource(id = R.string.profile_image_info),
            )
            Text(
                "Tim TUBES",
                fontFamily = poppinsFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlackColor500
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "tubes@tubes.com",
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.W400,
                color = GreyColor300
            )
        }
    }
}