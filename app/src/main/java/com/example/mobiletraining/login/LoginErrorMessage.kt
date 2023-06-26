package com.example.mobiletraining.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun LoginErrorMessage(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "Please enter valid email and password.",
        style = TextStyle(
            color = Color.Red,
            fontSize = 18.sp,
        ),
    )
}