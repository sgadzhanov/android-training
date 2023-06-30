package com.example.mobiletraining

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.firsttask.R
import com.example.mobiletraining.models.viewmodels.ProductsViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchBar(productsViewModel: ProductsViewModel) {
//    var filterText: String by remember { mutableStateOf("") }
//
//    TextField(
//        value = filterText,
//        onValueChange = { value ->
//            filterText = value
//            productsViewModel.filterProducts(value)
//        },
//        placeholder = { Text(text = stringResource(id = R.string.SEARCH)) },
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.SEARCH_FIELD_SHAPE))),
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = null,
//                modifier = Modifier.size(dimensionResource(id = R.dimen.SIZE_XL))
//            )
//        },
//        singleLine = true,
//        shape = MaterialTheme.shapes.small,
//        colors = TextFieldDefaults.textFieldColors(
//            focusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        )
//    )
//}
//fun SearchScreen(productsViewModel: ProductsViewModel) {
//    TextField(
//        modifier = Modifier.fillMaxWidth(),
//        value = productsViewModel.textFilter,
//        onValueChange = { text ->
//            productsViewModel.textFilter = text
//            productsViewModel.filterProducts(text)
//        },
//        placeholder = {
//            Text(
//                text = "Search",
//                style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
//            )
//        },
//        textStyle = MaterialTheme.typography.titleSmall.copy(color = Color.Gray),
//        leadingIcon = {
//            Icon(
//                Icons.Default.Search,
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(15.dp)
//                    .size(24.dp),
//            )
//        },
//        singleLine = true,
//        shape = RoundedCornerShape(50.dp),
////        color = TextFieldDefaults.textFieldColors(
////            focusedIndicatorColor = Color.Transparent,
////            disabledIndicatorColor = Color.Transparent,
////        )
//    )
//}