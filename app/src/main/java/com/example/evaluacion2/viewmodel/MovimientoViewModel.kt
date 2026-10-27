package com.example.evaluacion2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.data.AppDatabase
import com.example.evaluacion2.data.Movimiento
import com.example.evaluacion2.data.MovimientoRepository
import com.example.evaluacion2.data.TipoMovimiento
import com.example.evaluacion2.model.MovimientoFormulario
import com.example.evaluacion2.model.categoriasGasto
import com.example.evaluacion2.model.categoriasIngreso
import com.example.evaluacion2.util.fechaHoy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovimientoViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = MovimientoRepository(AppDatabase.getInstance(app).movimientoDao())

    val movimientos: StateFlow<List<Movimiento>> =
        repo.movimientos.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _formulario = MutableStateFlow(MovimientoFormulario())
    val formulario: StateFlow<MovimientoFormulario> = _formulario.asStateFlow()


    private fun validarMonto(v: String): String? {
        if (v.isBlank()) return "El monto es obligatorio"
        val n = v.toIntOrNull() ?: return "Solo números enteros"
        return when {
            n <= 0 -> "Debe ser mayor a 0"
            n > 5_000_000 -> "El monto es demasiado alto"
            else -> null
        }
    }

    private fun validarDescripcion(v: String): String? = when {
        v.isBlank() -> "La descripción es obligatoria"
        v.trim().length < 5 -> "Debe tener al menos 5 caracteres"
        else -> null
    }


    fun onTipoChange(tipo: TipoMovimiento) = _formulario.update {
        it.copy(
            tipo = tipo,
            categoria = if (tipo == TipoMovimiento.GASTO) categoriasGasto.first() else categoriasIngreso.first()
        )
    }

    fun onCategoriaChange(categoria: String) = _formulario.update { it.copy(categoria = categoria) }

    fun onMontoChange(valor: String) {
        val soloDigitos = valor.filter { it.isDigit() }
        _formulario.update { it.copy(monto = soloDigitos, errorMonto = validarMonto(soloDigitos)) }
    }

    fun onDescripcionChange(valor: String) =
        _formulario.update { it.copy(descripcion = valor, errorDescripcion = validarDescripcion(valor)) }

    fun onComprobante(uri: String?) = _formulario.update { it.copy(comprobanteUri = uri) }

    fun limpiar() { _formulario.value = MovimientoFormulario() }

    fun guardar(alExito: () -> Unit) {
        val f = _formulario.value
        val errorMonto = validarMonto(f.monto)
        val errorDescripcion = validarDescripcion(f.descripcion)
        if (errorMonto != null || errorDescripcion != null) {
            _formulario.update { it.copy(errorMonto = errorMonto, errorDescripcion = errorDescripcion) }
            return
        }
        viewModelScope.launch {
            repo.insertar(
                Movimiento(
                    tipo = f.tipo,
                    categoria = f.categoria,
                    descripcion = f.descripcion.trim(),
                    monto = f.monto.toInt(),
                    fecha = fechaHoy(),
                    comprobanteUri = f.comprobanteUri
                )
            )
            _formulario.value = MovimientoFormulario()
            alExito()
        }
    }
}
