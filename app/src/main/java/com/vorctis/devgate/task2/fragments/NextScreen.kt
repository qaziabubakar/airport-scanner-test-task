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

class NextScreen {

    @Composable
    fun content(navController: NavHostController) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Welcome to Screen 2")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Go to Screen 1")
                }
            }
        }
    }
}