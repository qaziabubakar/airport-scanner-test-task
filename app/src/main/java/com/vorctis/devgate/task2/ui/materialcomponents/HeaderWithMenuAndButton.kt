package com.vorctis.devgate.task2.ui.materialcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun HeaderWithMenuAndButton() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val (menuText, button) = createRefs()

        Text(
            text = "Menu",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(menuText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            onClick = { /* Create list action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFB9)),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                top.linkTo(menuText.top)
                bottom.linkTo(menuText.bottom)
            }
        ) {
            Text(text = "Create list", color = Color.Black)
        }
    }
}
