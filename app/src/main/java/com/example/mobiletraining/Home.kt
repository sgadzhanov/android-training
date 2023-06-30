package com.example.mobiletraining

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.firsttask.R
import com.example.mobiletraining.destinations.ProductDetailsDestination
import com.example.mobiletraining.models.viewmodels.ProductsViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.Black80
import com.example.mobiletraining.ui.theme.BrightPurple
import com.example.mobiletraining.ui.theme.DividerGray
import com.example.mobiletraining.ui.theme.PurpleRatingStar
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.utils.RatingStars
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

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
    //TODO change some of the names

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
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.PADDING_XXXL)),
                title = {
                    Text(
                        text = stringResource(id = R.string.HOME),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_LARGE).value.sp,
                    )
                },
                actions = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.PADDING_XXXL)),
                    )
                }
            )
        }, content = { paddingValues ->
            Box(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                Image(
                    painter = painterResource(R.drawable.pd_background),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(id = R.string.BACKGROUND_IMAGE_DESCRIPTION),
                    contentScale = ContentScale.Crop,
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(dimensionResource(id = R.dimen.SIZE_XXXL)),
                        color = Violet,
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.PADDING_XXL),
                                end = dimensionResource(id = R.dimen.PADDING_XXL),
                            )
                            .fillMaxSize()
                    ) {
                        SearchBar(productsViewModel = productsViewModel)
                        Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.PADDING_XXXL))) {
                            Text(
                                text = stringResource(id = R.string.NEW_PRODUCTS),
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_LARGE).value.sp,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(
                                        height = dimensionResource(id = R.dimen.SIZE_ICON_LARGE),
                                        width = dimensionResource(id = R.dimen.SIZE_ICON_XL),
                                    )
                                    .clickable {
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                            )
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
                                                top = dimensionResource(id = R.dimen.PADDING_LARGE_PLUS),
                                                bottom = dimensionResource(id = R.dimen.PADDING_XL),
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
                                                .padding(top = dimensionResource(id = R.dimen.PADDING_LARGE))
                                        ) {
                                            AsyncImage(
                                                model = currentProduct.image,
                                                contentDescription = currentProduct.short_description,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.PRODUCT_HOME_PAGE_IMAGE_SIZE))
                                                    .clip(
                                                        shape = RoundedCornerShape(
                                                            dimensionResource(id = R.dimen.SIZE_MEDIUM)
                                                        )
                                                    )
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .padding(start = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS))
                                            ) {
                                                Text(
                                                    text = "${stringResource(id = R.string.CATEGORY_TEMPLATE)}${currentProduct.category}",
                                                    style = MaterialTheme.typography.headlineSmall.copy(
                                                        color = Color.DarkGray,
                                                        fontSize = 12.sp,
                                                    ),
                                                )
                                                Text(
                                                    text = currentProduct.title,
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                                        color = Black80,
                                                    )
                                                )
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.PADDING_MEDIUM)
                                                    ),
                                                    text = currentProduct.short_description,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color.DarkGray,
                                                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_SMALL_PLUS).value.sp,
                                                    )
                                                )
                                                RatingStars(rating = currentProduct.rating)

                                                val formattedPrice =
                                                    DecimalFormat("0.00").format(currentProduct.price)
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = dimensionResource(id = R.dimen.PADDING_SMALL)
                                                    ),
                                                    text = "${stringResource(id = R.string.DOLLAR_SIGN)}${formattedPrice}",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                                )
                                            }
                                        }
                                    }
                                    Divider(
                                        color = DividerGray,
                                        thickness = dimensionResource(id = R.dimen.DIVIDER),
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
                                        start = dimensionResource(id = R.dimen.PADDING_XXXL),
                                        end = dimensionResource(id = R.dimen.PADDING_XXXL),
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
                                                    id = R.dimen.PADDING_XL,
                                                )
                                            ),
                                            text = stringResource(id = R.string.FILTERS),
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                color = Black,
                                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
                                            )
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = stringResource(id = R.string.SAVE),
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                color = BrightPurple,
                                                fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
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
                                        text = stringResource(id = R.string.PRICE_RANGE),
                                        modifier = Modifier.padding(
                                            top = dimensionResource(
                                                id = R.dimen.PADDING_XXXL,
                                            )
                                        ),
                                        color = Black,
                                        fontSize = dimensionResource(id = R.dimen.FONT_SIZE_MEDIUM).value.sp,
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
                                                id = R.dimen.PADDING_LARGE,
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
                                                top = dimensionResource(id = R.dimen.PADDING_MEDIUM_PLUS),
                                            )
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = "${stringResource(id = R.string.DOLLAR_SIGN)}${priceRange.start.toInt()}",
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                        Text(
                                            text = "${stringResource(id = R.string.DOLLAR_SIGN)}${priceRange.endInclusive.toInt()}",
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.PRODUCT_RATING),
                                        modifier = Modifier.padding(
                                            top = dimensionResource(
                                                id = R.dimen.PADDING_LARGE,
                                            )
                                        ),
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    LazyRow(
                                        modifier = Modifier
                                            .padding(
                                                top = dimensionResource(
                                                    id = R.dimen.PADDING_XS,
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
                                                    .size(dimensionResource(id = R.dimen.SIZE_ICON_LARGE))
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
        placeholder = { Text(text = stringResource(id = R.string.SEARCH)) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.SEARCH_FIELD_SHAPE))),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.SIZE_XL))
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