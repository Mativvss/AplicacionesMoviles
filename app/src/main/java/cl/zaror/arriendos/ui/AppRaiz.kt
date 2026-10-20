package cl.zaror.arriendos.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.zaror.arriendos.ui.screens.FinanzasScreen
import cl.zaror.arriendos.ui.screens.MovimientoFormScreen
import cl.zaror.arriendos.ui.screens.ReservasScreen
import cl.zaror.arriendos.viewmodel.MovimientoViewModel
import kotlinx.coroutines.launch

private const val RUTA_RESERVAS = "reservas"
private const val RUTA_FINANZAS = "finanzas"
private const val RUTA_NUEVO_MOVIMIENTO = "nuevo_movimiento"

@Composable
fun AppRaiz() {
    val nav = rememberNavController()
    val movimientoVm: MovimientoViewModel = viewModel()
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val ruta = nav.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            if (ruta == RUTA_RESERVAS || ruta == RUTA_FINANZAS) {
                NavigationBar {
                    NavigationBarItem(
                        selected = ruta == RUTA_RESERVAS,
                        onClick = { irA(nav, RUTA_RESERVAS) },
                        icon = { Icon(Icons.Filled.Event, "Reservas") },
                        label = { Text("Reservas") }
                    )
                    NavigationBarItem(
                        selected = ruta == RUTA_FINANZAS,
                        onClick = { irA(nav, RUTA_FINANZAS) },
                        icon = { Icon(Icons.Filled.AccountBalanceWallet, "Finanzas") },
                        label = { Text("Finanzas") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = RUTA_RESERVAS, modifier = Modifier.padding(padding)) {
            // Matias: agrega aqui tu ruta "nueva_reserva" y usa nav.navigate("nueva_reserva")
            composable(RUTA_RESERVAS) { ReservasScreen(nav) }
            composable(RUTA_FINANZAS) {
                FinanzasScreen(movimientoVm) { nav.navigate(RUTA_NUEVO_MOVIMIENTO) }
            }
            composable(RUTA_NUEVO_MOVIMIENTO) {
                MovimientoFormScreen(
                    vm = movimientoVm,
                    snackbar = snackbar,
                    alVolver = { nav.popBackStack() },
                    alExito = {
                        nav.popBackStack()
                        scope.launch { snackbar.showSnackbar("Movimiento guardado") }
                    }
                )
            }
        }
    }
}

private fun irA(nav: androidx.navigation.NavController, ruta: String) {
    nav.navigate(ruta) {
        popUpTo(RUTA_RESERVAS) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
