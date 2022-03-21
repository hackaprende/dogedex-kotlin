package com.hackaprende.dogedex.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    label: String,
    email: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(label) },
        value = email,
        onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation,
    )
}