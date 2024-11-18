package com.vorctis.devgate.task2.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vorctis.devgate.task2.utils.Routes

class HomeScreen {

    @Composable
    fun content(navController: NavHostController) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Welcome to my app!")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { navController.navigate(Routes.Next.route) }) {
                    Text(text = "Go to Screen 2")
                }
            }
        }
    }
}