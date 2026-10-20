package com.example.evaluacion2.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun CampoValidado(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    modifier: Modifier = Modifier,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFecha(
    fecha: Long?,
    onFecha: (Long?) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    var abierto by remember { mutableStateOf(false) }
    val texto = fecha?.let { formatoFecha(it) } ?: ""

    OutlinedTextField(
        value = texto,
        onValueChange = {},
        readOnly = true,
        label = { Text("Fecha del arriendo") },
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        trailingIcon = {
            IconButton(onClick = { abierto = true }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Elegir fecha")
            }
        },
        modifier = modifier.fillMaxWidth()
    )

    if (abierto) {
        val estado = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { abierto = false },
            confirmButton = {
                TextButton(onClick = {
                    onFecha(estado.selectedDateMillis)
                    abierto = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { abierto = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = estado)
        }
    }
}

@Composable
fun FilaSwitch(
    texto: String,
    activo: Boolean,
    onCambio: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(texto)
        Switch(checked = activo, onCheckedChange = onCambio)
    }
}

@Composable
fun FilaCheckbox(
    texto: String,
    marcado: Boolean,
    onCambio: (Boolean) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = marcado, onCheckedChange = onCambio)
            Text(texto)
        }
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

fun formatoFecha(milisegundos: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale("es", "CL"))
    formato.timeZone = TimeZone.getTimeZone("UTC")
    return formato.format(Date(milisegundos))
}