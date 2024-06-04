package com.tubes.travel_insight.ui.navigation

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.tubes.travel_insight.R
import com.tubes.travel_insight.ui.theme.poppinsFamily


@Composable
fun BottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_booking),
                icon = Icons.Default.CardTravel,
                screen = Screen.AllBookingScreen
            ),
            NavigationItem(
                title = stringResource(R.string.menu_review),
                icon = Icons.Default.RateReview,
                screen = Screen.AllReviewScreen,

            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title, fontFamily = poppinsFamily,
                ) },
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route) {
                        // popUpTo berfungsi saat tombol back ditekan, dia akan langsung ke halaman awal (startDestination)
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }

                }
            )
        }
    }
}
