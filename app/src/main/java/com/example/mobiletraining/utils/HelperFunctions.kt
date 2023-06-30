package com.example.mobiletraining.utils

import com.example.mobiletraining.utils.constants.Constants.Shared.price_format_pattern
import java.text.DecimalFormat

fun Int.asFormattedString(): String {
    val decimalFormat = DecimalFormat(price_format_pattern)
    return decimalFormat.format(this)
}
