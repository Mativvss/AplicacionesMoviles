package com.example.evaluacion2.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CampoValidado(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    modifier: Modifier = Modifier,
    tipoTeclado: KeyboardType = KeyboardType.Text
){
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = {Text(etiqueta)},
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        trailingIcon = {
            if (error != null) {
                Icon(Icons.Filled.Warning, contentDescription = "Campo con error")
            } else if (valor.isNotBlank()) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Campo válido",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}