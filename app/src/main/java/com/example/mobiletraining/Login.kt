package com.example.mobiletraining


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firsttask.R

const val EMAIL: String = "stamat@abv.bg"
const val PASSWORD: String = "123123"

@Composable
@Preview
fun Login(loginHandler: () -> Unit = {}) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailIsValid = remember { mutableStateOf(true) }
    val passwordIsValid = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.login__background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(6.dp)
                .padding(horizontal = 8.dp)
                .padding(top = 68.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
            )
            LoginTitle(modifier = Modifier.padding(top = 40.dp, bottom = 10.dp))
            EmailTextField(
                email = email.value,
                setEmailValue = { email.value = it },
                isValidEmail = emailIsValid.value,
                resetBorderColor = { emailIsValid.value = true },
                modifier = Modifier.padding(bottom = 10.dp),
            )
            PasswordTextField(
                password = password.value,
                setPassword = { password.value = it },
                isValidPassword = passwordIsValid.value,
                resetBorderColor = { passwordIsValid.value = true },
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            if (!emailIsValid.value || !passwordIsValid.value) {
                ErrorMessage()
            }

            Button(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .width(333.dp),
                enabled = emailIsValid.value && passwordIsValid.value,
                onClick = {
                    if (isValidEmail(email.value) && isValidPassword(password.value)) {
                        loginHandler()
                    }
                    emailIsValid.value = isValidEmail(email.value)
                    passwordIsValid.value = isValidPassword(password.value)
                    password.value = ""
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF47337A)),
            ) {
                Text("Log in", style = TextStyle(fontWeight = FontWeight.Bold))
            }
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
    modifier: Modifier = Modifier,
) {
    val isFocused = remember { mutableStateOf(false) }

    Row {
        TextField(
            value = email,
            onValueChange = {
                resetBorderColor(Color.Gray)
                setEmailValue(it)
            },
            label = { Text(text = "Email", style = TextStyle(color = Color.Black)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, end = 8.dp, start = 8.dp)
                .border(
                    1.dp,
                    color = if (!isValidEmail) Color.Red else if (isFocused.value) Color(0xFF722ED8) else Color.Gray,
                    shape = RoundedCornerShape(6.dp)
                )
                .background(color = Color.Transparent)
                .alpha(0.4f)
                .onFocusChanged { isFocused.value = it.isFocused },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(color = Color.Black)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    setPassword: (String) -> Unit,
    isValidPassword: Boolean,
    resetBorderColor: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isVisible = remember { mutableStateOf(false) }
    val isFocused = remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = {
            resetBorderColor(Color.Gray)
            setPassword(it)
        },
        label = { Text("Password", style = TextStyle(color = Color.Black)) },
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = if (!isValidPassword) Color.Red else if (isFocused.value) Color(0xFF722ED8) else Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .background(color = Color.Transparent)
            .alpha(0.4f)
            .onFocusChanged { isFocused.value = it.isFocused },
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        visualTransformation = if (isVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { isVisible.value = !isVisible.value }
            ) {
                val icon: ImageVector =
                    if (isVisible.value) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
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

@Composable
fun LoginTitle(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = "Log in",
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF47337A),
            )
        )
    }
}

@Composable
fun ErrorMessage() {
    Text(
        text = "Please enter valid email and password.",
        style = TextStyle(
            color = Color.Red,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
        )
    )
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