package com.example.evaluacion2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion2.ui.screens.ReservaFormScreen
import com.example.evaluacion2.ui.screens.ReservasScreen
import com.example.evaluacion2.ui.theme.Evaluacion2Theme
import com.example.evaluacion2.viewmodel.ReservaViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Evaluacion2Theme {
                val reservaViewModel: ReservaViewModel = viewModel()
                var pantalla by rememberSaveable { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = pantalla == 0,
                                onClick = { pantalla = 0 },
                                icon = { Icon(Icons.Filled.Add, contentDescription = "Nueva reserva") },
                                label = { Text("Nueva") }
                            )
                            NavigationBarItem(
                                selected = pantalla == 1,
                                onClick = { pantalla = 1 },
                                icon = { Icon(Icons.Filled.DateRange, contentDescription = "Reservas") },
                                label = { Text("Reservas") }
                            )
                        }
                    }
                ) { innerPadding ->
                    if (pantalla == 0) {
                        ReservaFormScreen(reservaViewModel, Modifier.padding(innerPadding))
                    } else {
                        ReservasScreen(reservaViewModel, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}