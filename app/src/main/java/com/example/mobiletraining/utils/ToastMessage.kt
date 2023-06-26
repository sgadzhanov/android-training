package com.example.mobiletraining.utils

import android.content.Context
import android.widget.Toast

object ToastMessage {
    fun showToastMessage(context: Context, message: String?) {
        Toast.makeText(context, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
    }
}
