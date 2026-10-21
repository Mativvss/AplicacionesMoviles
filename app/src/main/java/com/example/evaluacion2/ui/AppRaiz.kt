package com.example.evaluacion2.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion2.ui.screens.FinanzasScreen
import com.example.evaluacion2.ui.screens.MovimientoFormScreen
import com.example.evaluacion2.ui.screens.ReservaFormScreen
import com.example.evaluacion2.ui.screens.ReservasScreen
import com.example.evaluacion2.viewmodel.MovimientoViewModel
import com.example.evaluacion2.viewmodel.ReservaViewModel
import kotlinx.coroutines.launch

/** Pantallas de la app. La navegacion se maneja con una variable de estado (sin navigation-compose). */
private enum class Pantalla { NUEVA_RESERVA, RESERVAS, FINANZAS, NUEVO_MOVIMIENTO }

@Composable
fun AppRaiz() {
    var pantalla by rememberSaveable { mutableStateOf(Pantalla.RESERVAS) }

    val reservaVm: ReservaViewModel = viewModel()
    val movimientoVm: MovimientoViewModel = viewModel()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = pantalla == Pantalla.NUEVO_MOVIMIENTO) {
        movimientoVm.limpiar()
        pantalla = Pantalla.FINANZAS
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            if (pantalla != Pantalla.NUEVO_MOVIMIENTO) {
                NavigationBar {
                    NavigationBarItem(
                        selected = pantalla == Pantalla.NUEVA_RESERVA,
                        onClick = { pantalla = Pantalla.NUEVA_RESERVA },
                        icon = { Icon(Icons.Filled.AddCircle, "Nueva reserva") },
                        label = { Text("Nueva reserva") }
                    )
                    NavigationBarItem(
                        selected = pantalla == Pantalla.RESERVAS,
                        onClick = { pantalla = Pantalla.RESERVAS },
                        icon = { Icon(Icons.Filled.Event, "Reservas") },
                        label = { Text("Reservas") }
                    )
                    NavigationBarItem(
                        selected = pantalla == Pantalla.FINANZAS,
                        onClick = { pantalla = Pantalla.FINANZAS },
                        icon = { Icon(Icons.Filled.AccountBalanceWallet, "Finanzas") },
                        label = { Text("Finanzas") }
                    )
                }
            }
        }
    ) { padding ->
        val modificador = Modifier.padding(padding)
        when (pantalla) {
            Pantalla.NUEVA_RESERVA -> ReservaFormScreen(reservaVm, modificador)
            Pantalla.RESERVAS -> ReservasScreen(reservaVm, modificador)
            Pantalla.FINANZAS -> Box(modificador) {
                FinanzasScreen(movimientoVm) { pantalla = Pantalla.NUEVO_MOVIMIENTO }
            }
            Pantalla.NUEVO_MOVIMIENTO -> Box(modificador) {
                MovimientoFormScreen(
                    vm = movimientoVm,
                    snackbar = snackbar,
                    alVolver = { pantalla = Pantalla.FINANZAS },
                    alExito = {
                        pantalla = Pantalla.FINANZAS
                        scope.launch { snackbar.showSnackbar("Movimiento guardado") }
                    }
                )
            }
        }
    }
}
