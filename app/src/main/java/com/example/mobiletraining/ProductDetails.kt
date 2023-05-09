package com.example.mobiletraining


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firsttask.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails(goBackHandler: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item") },
                navigationIcon = {
                    IconButton(onClick = { goBackHandler() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    BadgedBox(badge = { Text("3") }) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.ShoppingBasket, contentDescription = "Bag")
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 65.dp)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.radio),
                    contentDescription = "Radio",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Best DAB Radio",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    Text("5")
                    repeat(5) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                    }
                }
                Row {
                    Text(
                        text = "Category: Home",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Gray,
                        )
                    )
                    Spacer(Modifier.height(35.dp))
                }
                Row(
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Text(
                        text = "By default, titles are left aligned on desktop. The default position of titles on mobile and tablet depends on the platform. More information on this is available in cross-platform adaptation. If title text is long, use a prominent app bar and wrap the title to two lines.",
                        style = TextStyle(color = Color.DarkGray, fontSize = 16.sp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row() {
                    Text(
                        text = "$90.00",
                        style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    )
                }
                Spacer(Modifier.height(40.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "+ Add to cart")
                }
            }
        }
    }
}
