package com.ns.shopify.presentation.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertDialog(
    title: String,
    message: String?,
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = title)
        },
        text = {
            Column {
                if(message != null) {
                    Text(text = message)
                } else {
                    Text(text = "Something went wrong")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onClose) {
                Text(text = "OK")
            }
        }
    )
}
