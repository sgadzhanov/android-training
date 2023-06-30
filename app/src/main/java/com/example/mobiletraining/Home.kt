package com.example.mobiletraining

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.firsttask.R
import com.example.mobiletraining.destinations.CartDestination
import com.example.mobiletraining.destinations.ProductDetailsDestination
import com.example.mobiletraining.models.viewmodels.ProductsViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.BrightPurple
import com.example.mobiletraining.ui.theme.DividerGray
import com.example.mobiletraining.ui.theme.PurpleRatingStar
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.ui.theme.White
import com.example.mobiletraining.utils.RatingStars
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun Home(destinationsNavigator: DestinationsNavigator) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var ratingFilter by remember { mutableStateOf(1) }
    var priceRange by remember { mutableStateOf(0f..200f) }
    var selectedCategories by remember { mutableStateOf(mutableListOf<String>()) }
    val isLoading = productsViewModel.isLoading

    productsViewModel.allCategories.let { categories ->
        selectedCategories = categories.map { category -> category.name } as MutableList<String>
    }

    fun selectCategory(category: String) {
        selectedCategories = selectedCategories.toMutableList().apply { add(category) }
    }

    fun deselectCategory(category: String) {
        selectedCategories = selectedCategories.toMutableList().apply { remove(category) }
    }

    Scaffold(
        containerColor = Transparent,
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_xxxl)),
                title = {
                    Text(
                        text = stringResource(id = R.string.home),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        fontSize = dimensionResource(id = R.dimen.font_size_xl).value.sp,
                    )
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.size_medium)))
                        Box {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(
                                        height = dimensionResource(id = R.dimen.size_icon_large),
                                        width = dimensionResource(id = R.dimen.size_icon_xl),
                                    )
                                    .clickable { destinationsNavigator.navigate(CartDestination) }
                            )
                            val productsCount = GlobalState.cartProducts.size
                            if (productsCount != 0) {
                                Box(
                                    modifier = Modifier
                                        .size(dimensionResource(id = R.dimen.size_large))
                                        .background(color = PurpleRatingStar, CircleShape)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Text(
                                        text = productsCount.toString(),
                                        color = White,
                                        fontSize = dimensionResource(id = R.dimen.font_size_small).value.sp,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }

                }
            )
        }, content = { paddingValues ->
            Box(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                Image(
                    painter = painterResource(R.drawable.pd_background),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(id = R.string.background_image_description),
                    contentScale = ContentScale.Crop,
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(dimensionResource(id = R.dimen.size_xxxl)),
                        color = Violet,
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_xxl),
                                end = dimensionResource(id = R.dimen.padding_xxl),
                            )
                            .fillMaxSize()
                    ) {
                        SearchBar(productsViewModel = productsViewModel)
                        Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xxxl))) {
                            Text(
                                text = stringResource(id = R.string.new_products),
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = dimensionResource(id = R.dimen.font_size_xl).value.sp,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(
                                            height = dimensionResource(id = R.dimen.size_icon_large),
                                            width = dimensionResource(id = R.dimen.size_icon_xl),
                                        )
                                        .clickable {
                                            coroutineScope.launch {
                                                sheetState.show()
                                            }
                                        }
                                )
                                val currentFiltersCount = productsViewModel.activeFiltersCount.value
                                if (currentFiltersCount != 0) {
                                    Box(
                                        modifier = Modifier
                                            .size(dimensionResource(id = R.dimen.size_large))
                                            .background(color = PurpleRatingStar, CircleShape)
                                            .align(Alignment.TopEnd),
                                    ) {
                                        Text(
                                            text = currentFiltersCount.toString(),
                                            color = White,
                                            fontSize = dimensionResource(id = R.dimen.font_size_small).value.sp,
                                            modifier = Modifier.align(Alignment.Center),
                                        )
                                    }
                                }
                            }
                        }
                        productsViewModel.filteredProducts.let { products ->
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(products.size) { index ->
                                    val currentProduct = products[index]

                                    Box(
                                        modifier = Modifier
                                            .padding(
                                                top = dimensionResource(id = R.dimen.padding_large_plus),
                                                bottom = dimensionResource(id = R.dimen.padding_xl),
                                            )
                                            .clickable {
                                                destinationsNavigator.navigate(
                                                    ProductDetailsDestination(
                                                        currentProduct.id.toString()
                                                    )
                                                )
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(top = dimensionResource(id = R.dimen.padding_large))
                                        ) {
                                            AsyncImage(
                                                model = currentProduct.image,
                                                contentDescription = currentProduct.short_description,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.product_home_page_image_size))
                                                    .clip(
                                                        shape = RoundedCornerShape(
                                                            dimensionResource(id = R.dimen.size_medium)
                                                        )
                                                    )
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .padding(start = dimensionResource(id = R.dimen.padding_medium_plus))
                                            ) {
                                                Text(
                                                    text = "${stringResource(id = R.string.category_template)}${currentProduct.category}",
                                                    style = MaterialTheme.typography.headlineSmall.copy(
                                                        color = Color.DarkGray,
                                                        fontSize = 12.sp,
                                                    ),
                                                )
                                                Text(
                                                    text = currentProduct.title,
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                                        color = Black80,
                                                    )
                                                )
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.padding_medium)
                                                    ),
                                                    text = currentProduct.short_description,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color.DarkGray,
                                                        fontSize = dimensionResource(id = R.dimen.font_size_small_plus).value.sp,
                                                    )
                                                )
                                                RatingStars(rating = currentProduct.rating)

                                                val formattedPrice =
                                                    DecimalFormat("0.00").format(currentProduct.price)
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.padding_small)
                                                    ),
                                                    text = "${stringResource(id = R.string.dollar_sign)}${formattedPrice}",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                                )
                                            }
                                        }
                                    }
                                    Divider(
                                        color = DividerGray.copy(alpha = 0.5f),
                                        thickness = dimensionResource(id = R.dimen.divider),
                                    )
                                }
                            }
                        }
                        if (sheetState.isVisible) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    coroutineScope.launch {
                                        sheetState.hide()
                                    }
                                },
                                modifier = Modifier.fillMaxSize(),
                                sheetState = sheetState
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = dimensionResource(id = R.dimen.padding_xxxl),
                                        end = dimensionResource(id = R.dimen.padding_xxxl),
                                    )
                                ) {
                                    Row {
                                        Icon(Icons.Outlined.Close, contentDescription = null,
                                            modifier = Modifier.clickable {
                                                productsViewModel.removeAllFilters()
                                                coroutineScope.launch {
                                                    sheetState.hide()
                                                }
                                            })
                                        Text(
                                            modifier = Modifier.padding(
                                                start = dimensionResource(
                                                    id = R.dimen.padding_xl,
                                                )
                                            ),
                                            text = stringResource(id = R.string.filters),
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                color = Black,
                                                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                            )
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = stringResource(id = R.string.save),
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                color = BrightPurple,
                                                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                            ),
                                            modifier = Modifier.clickable {
                                                productsViewModel.applyFilters(
                                                    selectedCategories,
                                                    ratingFilter,
                                                    priceRange,
                                                )
                                                coroutineScope.launch {
                                                    sheetState.hide()
                                                }
                                            }
                                        )
                                    }
                                    CategoriesFilter(
                                        productsViewModel = productsViewModel,
                                        selectedCategories = selectedCategories,
                                        ::selectCategory,
                                        ::deselectCategory,
                                    )
                                    Text(
                                        text = stringResource(id = R.string.price_range),
                                        modifier = Modifier.padding(
                                            top = dimensionResource(
                                                id = R.dimen.padding_xxxl,
                                            )
                                        ),
                                        color = Black,
                                        fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                                    )
                                    RangeSlider(
                                        value = priceRange,
                                        onValueChange = { value ->
                                            priceRange = value.start.roundToInt()
                                                .toFloat()..value.endInclusive.roundToInt()
                                                .toFloat()
                                        },
                                        valueRange = 0f..150f,
                                        modifier = Modifier.padding(
                                            top = dimensionResource(
                                                id = R.dimen.padding_large,
                                            )
                                        ),
                                        colors = SliderDefaults.colors(
                                            thumbColor = PurpleRatingStar,
                                            activeTrackColor = PurpleRatingStar,
                                            inactiveTrackColor = PurpleRatingStar,
                                            activeTickColor = PurpleRatingStar,
                                            inactiveTickColor = PurpleRatingStar,
                                            disabledThumbColor = PurpleRatingStar,
                                            disabledActiveTrackColor = PurpleRatingStar,
                                            disabledActiveTickColor = PurpleRatingStar,
                                            disabledInactiveTrackColor = PurpleRatingStar,
                                            disabledInactiveTickColor = PurpleRatingStar
                                        )
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                top = dimensionResource(id = R.dimen.padding_medium_plus),
                                            )
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = "${stringResource(id = R.string.dollar_sign)}${priceRange.start.toInt()}",
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                        Text(
                                            text = "${stringResource(id = R.string.dollar_sign)}${priceRange.endInclusive.toInt()}",
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.product_rating),
                                        modifier = Modifier.padding(
                                            top = dimensionResource(
                                                id = R.dimen.padding_large,
                                            )
                                        ),
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    LazyRow(
                                        modifier = Modifier
                                            .padding(
                                                top = dimensionResource(
                                                    id = R.dimen.padding_xs,
                                                )
                                            )
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        items(5) { index ->
                                            val currentStar = index + 1

                                            Icon(
                                                painter = painterResource(
                                                    id = if (currentStar > ratingFilter) R.drawable.star
                                                    else R.drawable.empty_star
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.size_icon_large))
                                                    .clickable { ratingFilter = currentStar },
                                                tint = PurpleRatingStar,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(productsViewModel: ProductsViewModel) {
    var filterText: String by remember { mutableStateOf("") }

    TextField(
        value = filterText,
        onValueChange = { value ->
            filterText = value
            productsViewModel.filterProducts(value)
        },
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.search_filed_shape))),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_xl))
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
        )
    )
}