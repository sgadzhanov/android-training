package com.example.mobiletraining.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPasswordTextField(
    modifier: Modifier,
    password: String,
    setPassword: (String) -> Unit,
    isValidPassword: Boolean,
    resetBorderColor: (Color) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = if (!isValidPassword) Color.Red else if (isFocused) Color(0xFF722ED8) else Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .background(color = Color.Transparent)
            .alpha(0.4f)
            .onFocusChanged { isFocused = it.isFocused },
        value = password,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setPassword(it)
        },
        label = { Text("Password", style = TextStyle(color = Color.Black)) },
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { isVisible = !isVisible }
            ) {
                val icon: ImageVector =
                    if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                Icon(icon, contentDescription = "tease password")
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}
