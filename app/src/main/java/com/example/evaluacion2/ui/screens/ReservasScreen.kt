package com.example.evaluacion2.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.data.EstadoReserva
import com.example.evaluacion2.data.Reserva
import com.example.evaluacion2.ui.componentes.formatoFecha
import com.example.evaluacion2.viewmodel.ReservaViewModel

@Composable
fun ReservasScreen(viewModel: ReservaViewModel, modifier: Modifier = Modifier) {
    val reservas by viewModel.reservas.collectAsState()

    if (reservas.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aún no hay reservas registradas")
        }
    } else {
        LazyColumn(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reservas, key = { it.id }) { reserva ->
                TarjetaReserva(
                    reserva = reserva,
                    onConfirmar = { viewModel.confirmar(reserva.id) },
                    onCancelar = { viewModel.cancelar(reserva.id) }
                )
            }
        }
    }
}
@Composable
private fun TarjetaReserva(
    reserva: Reserva,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    val colorObjetivo = when (reserva.estado) {
        EstadoReserva.PENDIENTE -> MaterialTheme.colorScheme.surfaceVariant
        EstadoReserva.CONFIRMADA -> MaterialTheme.colorScheme.primaryContainer
        EstadoReserva.CANCELADA -> MaterialTheme.colorScheme.errorContainer
    }
    val color by animateColorAsState(
        targetValue = colorObjetivo,
        animationSpec = tween(durationMillis = 600),
        label = "colorEstado"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(reserva.vecino, style = MaterialTheme.typography.titleMedium)
            Text("Fecha: ${formatoFecha(reserva.fecha)}")
            Text("Horario: ${reserva.horaInicio} a ${reserva.horaFin}")
            Text("Monto: \$${reserva.monto}")
            Text("Cocina: ${if (reserva.usaCocina) "Sí" else "No"}")
            Text("Estado: ${reserva.estado}")

            if (reserva.estado == EstadoReserva.PENDIENTE) {
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = onConfirmar) { Text("Confirmar") }
                    OutlinedButton(onClick = onCancelar) { Text("Cancelar") }
                }
            }
        }
    }
}

