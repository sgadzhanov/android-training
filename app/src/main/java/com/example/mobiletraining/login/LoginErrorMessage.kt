package com.example.mobiletraining.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.firsttask.R
import com.example.mobiletraining.ui.theme.ErrorColor

@Composable
fun LoginErrorMessage(modifier: Modifier) {
    //TODO: thing about the actual design implementation
    Text(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.VALIDATION_MESSAGE),
        style = TextStyle(
            color = ErrorColor,
            fontSize = 18.sp,
        ),
    )
}