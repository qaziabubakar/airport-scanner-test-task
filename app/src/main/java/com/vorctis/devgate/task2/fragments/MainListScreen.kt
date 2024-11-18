package com.vorctis.devgate.task2.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vorctis.devgate.task2.ui.materialcomponents.HeaderWithMenuAndButton
import com.vorctis.devgate.task2.utils.Routes

class MainListScreen {

    @Composable
    fun content(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000028))
                .padding(16.dp)
        ) {
            HeaderWithMenuAndButton()

            Text(
                text = "Locations: 14",
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(10) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(0xFF1A1A3B), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Gate 11A",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "${index + 3} Bags",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate(Routes.SearchBagTagScreen.route) },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00CCCC)
                    ),
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(text = "Search bag tag", color = Color.Black)
                }

                Button(
                    onClick = { /* Scan Location Action */ },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00CCCC)
                    ),
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(text = "Scan location", color = Color.Black)
                }
            }
        }
    }
}