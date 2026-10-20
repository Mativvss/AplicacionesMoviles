package com.example.evaluacion2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.viewmodel.ReservaViewModel

@Composable
fun ReservaFormScreen(viewModel: ReservaViewModel, modifier: Modifier= Modifier){
    val formulario by viewModel.formulario.collectAsState()
    val errores by viewModel.errores.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
        OutlinedTextField(
            value = formulario.vecino,
            onValueChange = { viewModel.actualizarVecino(it) },
            label = { Text("Nombre del vecino/a") },
            singleLine = true,
            isError = errores.vecino != null,
            supportingText = { errores.vecino?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = formulario.correo,
            onValueChange = { viewModel.actualizarCorreo(it) },
            label = { Text("Ingrese su correo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errores.correo != null,
            supportingText = { errores.correo?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = formulario.telefono,
            onValueChange = {viewModel.actualizarTelefono(it)},
            label = { Text("Teléfono") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = errores.telefono != null,
            supportingText = { errores.telefono?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.validarFormulario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar solicitud")
        }
    }
}