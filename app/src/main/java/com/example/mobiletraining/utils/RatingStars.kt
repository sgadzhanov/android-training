package com.example.mobiletraining.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.firsttask.R
import com.example.mobiletraining.ui.theme.PurpleRatingStar

@Composable
fun RatingStars(rating: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = rating.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL_PLUS).value.sp,
            )
        )
        repeat(rating) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.SIZE_ICON_XS))
                    .align(Alignment.CenterVertically),
                tint = PurpleRatingStar,
            )
        }
    }
}