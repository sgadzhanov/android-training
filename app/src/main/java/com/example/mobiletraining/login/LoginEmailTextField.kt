package com.example.mobiletraining.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firsttask.R
import com.example.mobiletraining.ui.theme.ErrorColor

@Composable
fun LoginEmailTextField(
    modifier: Modifier,
    email: String,
    setEmailValue: (String) -> Unit,
    isValidEmail: Boolean,
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.PADDING_LARGE)),
        value = email,
        label = { Text(text = stringResource(id = R.string.EMAIL)) },
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = ErrorColor,
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        isError = !isValidEmail,
        onValueChange = { newValue -> setEmailValue(newValue) }
    )
}