package com.example.evaluacion2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.ui.componentes.CampoFecha
import com.example.evaluacion2.ui.componentes.CampoValidado
import com.example.evaluacion2.ui.componentes.FilaCheckbox
import com.example.evaluacion2.ui.componentes.FilaSwitch
import com.example.evaluacion2.viewmodel.ReservaViewModel

@Composable
fun ReservaFormScreen(viewModel: ReservaViewModel, modifier: Modifier = Modifier) {
    val formulario by viewModel.formulario.collectAsState()
    val errores by viewModel.errores.collectAsState()
    val contexto = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CampoValidado(
            valor = formulario.vecino,
            onCambio = { viewModel.actualizarVecino(it) },
            etiqueta = "Nombre del vecino/a",
            error = errores.vecino
        )
        CampoValidado(
            valor = formulario.correo,
            onCambio = { viewModel.actualizarCorreo(it) },
            etiqueta = "Correo",
            error = errores.correo,
            tipoTeclado = KeyboardType.Email
        )
        CampoValidado(
            valor = formulario.telefono,
            onCambio = { viewModel.actualizarTelefono(it) },
            etiqueta = "Teléfono",
            error = errores.telefono,
            tipoTeclado = KeyboardType.Phone
        )
        CampoFecha(
            fecha = formulario.fecha,
            onFecha = { viewModel.actualizarFecha(it) },
            error = errores.fecha
        )
        CampoValidado(
            valor = formulario.horaInicio,
            onCambio = { viewModel.actualizarHoraInicio(it) },
            etiqueta = "Hora de inicio (HH:mm)",
            error = errores.horaInicio
        )
        CampoValidado(
            valor = formulario.horaFin,
            onCambio = { viewModel.actualizarHoraFin(it) },
            etiqueta = "Hora de fin (HH:mm)",
            error = errores.horaFin
        )
        CampoValidado(
            valor = formulario.monto,
            onCambio = { viewModel.actualizarMonto(it) },
            etiqueta = "Monto del arriendo (\$)",
            error = errores.monto,
            tipoTeclado = KeyboardType.Number
        )
        FilaSwitch(
            texto = "¿Usará la cocina?",
            activo = formulario.usaCocina,
            onCambio = { viewModel.actualizarUsaCocina(it) }
        )
        FilaCheckbox(
            texto = "Acepto el reglamento del salón",
            marcado = formulario.aceptaReglamento,
            onCambio = { viewModel.actualizarReglamento(it) },
            error = errores.aceptaReglamento
        )
        Button(
            onClick = {
                viewModel.guardar {
                    Toast.makeText(contexto, "Reserva registrada", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar solicitud")
        }
    }
}