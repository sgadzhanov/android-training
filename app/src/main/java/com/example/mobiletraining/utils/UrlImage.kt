package com.example.mobiletraining.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.firsttask.R

@Composable
fun UrlImage(url: String?, description: String?) {
    val imagePainter = rememberAsyncImagePainter(url)

    Image(
        painter = imagePainter,
        contentDescription = description,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_xl)))
            .size(
                width = dimensionResource(id = R.dimen.product_details_image_width),
                height = dimensionResource(id = R.dimen.product_details_image_height),
            )
    )
}