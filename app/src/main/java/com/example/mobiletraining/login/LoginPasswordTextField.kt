package com.example.mobiletraining.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.firsttask.R
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.ErrorColor

@Composable
fun LoginPasswordTextField(
    modifier: Modifier,
    password: String,
    setPassword: (String) -> Unit,
    isValidPassword: Boolean,
) {
    var isVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = {
            setPassword(it)
        },
        label = {
            Text(
                text = stringResource(id = R.string.PASSWORD),
                style = TextStyle(color = Black)
            )
        },
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = ErrorColor,
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        isError = !isValidPassword,
        textStyle = TextStyle(fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { isVisible = !isVisible }
            ) {
                val icon: ImageVector =
                    if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                Icon(icon, contentDescription = null)
            }
        },
    )
}
