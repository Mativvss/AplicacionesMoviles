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
import com.example.evaluacion2.ui.theme.Evaluacion2Theme
import com.example.evaluacion2.viewmodel.ReservaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Evaluacion2Theme {
                val reservaViewModel: ReservaViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReservaFormScreen(
                        viewModel = reservaViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}