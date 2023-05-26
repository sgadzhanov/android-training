package com.example.mobiletraining.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginEmailTextField(
    email: String,
    setEmailValue: (String) -> Unit,
    isValidEmail: Boolean,
    resetBorderColor: (Color) -> Unit,
    modifier: Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = email,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setEmailValue(it)
        },
        label = { Text(text = "Email", style = TextStyle(color = Color.Black)) },
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = if (!isValidEmail) Color.Red else if (isFocused) Color(0xFF722ED8) else Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .background(color = Color.Transparent)
            .alpha(0.4f)
            .onFocusChanged { isFocused = it.isFocused },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(color = Color.Black)
    )
}