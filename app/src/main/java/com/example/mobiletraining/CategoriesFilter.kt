package com.example.mobiletraining

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.firsttask.R
import com.example.mobiletraining.models.viewmodels.ProductsViewModel
import com.example.mobiletraining.ui.theme.Black
import com.example.mobiletraining.ui.theme.BrightPurple
import com.example.mobiletraining.ui.theme.DarkGray
import com.example.mobiletraining.ui.theme.Violet
import com.example.mobiletraining.utils.constants.Constants.CategoriesFilterConstants.not_rotated
import com.example.mobiletraining.utils.constants.Constants.CategoriesFilterConstants.rotated
import com.example.mobiletraining.utils.constants.Constants.Shared.spacer_weight
import com.example.mobiletraining.utils.constants.Constants.Shared.width_90

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesFilter(
    productsViewModel: ProductsViewModel,
    selectedCategories: List<String>,
    selectCategory: (String) -> Unit,
    deselectCategory: (String) -> Unit,
) {
    var isToggled by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = if (isToggled) not_rotated else rotated
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_xxl))
    ) {
        Card(
            onClick = { isToggled = !isToggled },
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Black,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.category),
                        modifier = Modifier
                            .fillMaxWidth(width_90),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(id = R.string.all_categories),
                        fontSize = dimensionResource(id = R.dimen.font_size_small_plus).value.sp,
                        color = DarkGray,
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.ExpandMore,
                    tint = BrightPurple,
                    modifier = Modifier
                        .rotate(rotation.value)
                        .size(dimensionResource(id = R.dimen.size_icon_large)),
                    contentDescription = null,
                )
            }
        }
        AnimatedVisibility(visible = isToggled) {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                ) {
                    items(productsViewModel.allCategories.size) { index ->
                        val category = productsViewModel.allCategories[index]
                        val categoryName = category.name
                        Row(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.padding_large_plus))
                                .height(dimensionResource(id = R.dimen.size_xxxl))
                                .clickable {
                                    if (selectedCategories.contains(categoryName)) deselectCategory(
                                        categoryName
                                    )
                                    else selectCategory(categoryName)
                                }
                        ) {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                            )
                            Spacer(modifier = Modifier.weight(spacer_weight))
                            Box {
                                if (selectedCategories.contains(categoryName)) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Violet,
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