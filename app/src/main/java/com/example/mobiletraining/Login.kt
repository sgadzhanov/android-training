package com.example.mobiletraining


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val EMAIL: String = "stamat@abv.bg"
const val PASSWORD: String = "123123"

@Composable
fun Login(loginHandler: () -> Unit = {}) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (emailIsValid, setEmailIsValid) = remember { mutableStateOf(true) }
    val (passwordIsValid, setPasswordIsValid) = remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(6.dp)) {
        LoginTitle()
        EmailTextField(
            email = email,
            setEmailValue = { setEmail(it) },
            isValidEmail = emailIsValid,
            resetBorderColor = { setEmailIsValid(true) }
        )
        PasswordTextField(
            password = password,
            setPassword = { setPassword(it) },
            isValidPassword = passwordIsValid,
            resetBorderColor = { setPasswordIsValid(true) }
        )

        ErrorMessage(
            emailIsValid = emailIsValid,
            passwordIsValid = passwordIsValid,
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = emailIsValid && passwordIsValid,
            onClick = {
                if (isValidPassword(password) && isValidEmail(email)) {
                    loginHandler()
                }
                setEmailIsValid(isValidEmail(email))
                setPasswordIsValid(isValidPassword(password))
                setPassword("")
            }) {
            Text("Log in", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailTextField(
    email: String,
    setEmailValue: (String) -> Unit,
    isValidEmail: Boolean,
    resetBorderColor: (Color) -> Unit,
) {

    TextField(
        value = email,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setEmailValue(it)
        },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, end = 8.dp, start = 8.dp)
            .border(
                1.dp,
                color = if (isValidEmail) Color.Gray else Color.Red,
                shape = RoundedCornerShape(6.dp)
            ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    setPassword: (String) -> Unit,
    isValidPassword: Boolean,
    resetBorderColor: (Color) -> Unit,
) {
    val (isVisible, setIsVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setPassword(it)
        },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 12.dp)
            .border(
                1.dp,
                color = if (isValidPassword) Color.Gray else Color.Red,
                shape = RoundedCornerShape(6.dp)
            ),
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { setIsVisible(!isVisible) }) {
                val icon: ImageVector =
                    if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                Icon(icon, contentDescription = "tease password")
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun LoginTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp, bottom = 6.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = "Log in",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ErrorMessage(
    emailIsValid: Boolean,
    passwordIsValid: Boolean,
) {
    if (emailIsValid && passwordIsValid) return
    Row {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = "Please enter valid email and password.",
            style = TextStyle(color = Color.Red, fontSize = 18.sp)
        )
    }
}

private fun isValidEmail(email: String): Boolean {
//    val regex = Patterns.EMAIL_ADDRESS
//    return regex.matcher(email).matches()
    return email == EMAIL
}

private fun isValidPassword(password: String): Boolean {
    return password == PASSWORD
//    return password.trim().isNotEmpty() && password.length >= 6
}