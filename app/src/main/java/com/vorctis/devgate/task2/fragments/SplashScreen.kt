package com.vorctis.devgate.task2.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vorctis.devgate.task2.utils.Routes
import kotlinx.coroutines.delay

class SplashScreen {

    @Composable
    fun content(navController: NavHostController) {

        // Navigate to the MainListScreen after 2 seconds
        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate(Routes.MainListScreen.route) {
                popUpTo(Routes.Splash.route) { inclusive = true } // Removing Splash from backstack
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000028)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Surface(
                    color = Color(0xFF1A1A3B),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(160.dp, 60.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Company Logo",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                Text(
                    text = "Welcome",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
