package com.ns.shopify.presentation.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */

@Composable
fun CustomTextField(
    placeholder: String, label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    errorState: MutableState<Boolean>,
    onChanged: (TextFieldValue) -> Unit,
    icon : ImageVector? = null,
    initialValue: String = ""
) {
    //state
    var text by remember {
        mutableStateOf(TextFieldValue(initialValue))
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onChanged(newText)
        },
        placeholder = {
            Text(text = placeholder)
        },
        label = { Text(text = label) },
        shape = RoundedCornerShape(1.dp),
        trailingIcon = {
            if(icon != null) {
                Icon(
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = "TextField Email"
                )
            } else {

            }
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            trailingIconColor = if (text.text.isNotEmpty()) MaterialTheme.colors.primary else MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.primary,
            focusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.primary
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        isError = errorState.value,
        modifier = Modifier
            .fillMaxWidth()
    )
}