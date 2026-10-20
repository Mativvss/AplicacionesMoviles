package com.example.evaluacion2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.data.AppDatabase
import com.example.evaluacion2.data.Reserva
import com.example.evaluacion2.data.ReservaRepository
import com.example.evaluacion2.domain.Validadores
import com.example.evaluacion2.model.ReservaErrores
import com.example.evaluacion2.model.ReservaFormulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservaViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ReservaRepository(AppDatabase.getInstance(app).reservaDao())

    val reservas: StateFlow<List<Reserva>> =
        repo.reservas.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _formulario = MutableStateFlow(ReservaFormulario())
    val formulario: StateFlow<ReservaFormulario> = _formulario.asStateFlow()

    private val _errores = MutableStateFlow(ReservaErrores())
    val errores: StateFlow<ReservaErrores> = _errores.asStateFlow()

    fun actualizarTelefono (valor: String) {
        val limpio = valor.filter { it.isDigit()}.take(9)
        _formulario.value = _formulario.value.copy(telefono = limpio)
        _errores.value = _errores.value.copy(telefono = Validadores.telefono(limpio))
    }
    fun validarFormulario(): Boolean{
        val f = _formulario.value
        val nuevos = ReservaErrores(
            vecino = Validadores.nombre(f.vecino),
            correo = Validadores.correo(f.correo),
            telefono = Validadores.telefono(f.telefono),
            horaInicio = Validadores.hora(f.horaInicio),
            horaFin = Validadores.rangoHoras(f.horaInicio,f.horaFin),
            monto = Validadores.monto(f.monto),
            fecha = Validadores.fecha(f.fecha),
            aceptaReglamento = Validadores.reglamento(f.aceptaReglamento))
        _errores.value = nuevos
        return nuevos == ReservaErrores()
    }

    fun actualizarVecino(valor: String){
        _formulario.value = _formulario.value.copy(vecino=valor)
        _errores.value = _errores.value.copy(vecino = Validadores.nombre(valor))
    }
    fun actualizarCorreo(valor: String){
        _formulario.value = _formulario.value.copy(correo = valor)
        _errores.value = _errores.value.copy(correo = Validadores.correo(valor))
    }
    fun actualizarHoraInicio(valor : String){
        _formulario.value = _formulario.value.copy(horaInicio = valor)
        val fin = _formulario.value.horaFin
        _errores.value = _errores.value.copy(
            horaInicio = Validadores.hora(valor),
            horaFin = if(fin.isBlank()) _errores.value.horaFin
            else Validadores.rangoHoras(valor,fin)
        )
    }

    fun actualizarHoraFin(valor: String){
        _formulario.value = _formulario.value.copy(horaFin = valor)
        _errores.value = _errores.value.copy(
            horaFin = Validadores.rangoHoras(_formulario.value.horaInicio, valor)
        )
    }
    fun actualizarMonto(valor: String){
        val limpio = valor.filter {it.isDigit()}.take(7)
        _formulario.value = _formulario.value.copy(monto = limpio)
        _errores.value = _errores.value.copy(monto = Validadores.monto(limpio))
    }

    fun actualizarFecha(valor: Long?){
        _formulario.value = _formulario.value.copy(fecha = valor)
        _errores.value = _errores.value.copy(fecha = Validadores.fecha(valor))
    }

    fun actualizarUsaCocina(valor: Boolean){
        _formulario.value = _formulario.value.copy(usaCocina = valor)
    }
    fun actualizarReglamento(valor: Boolean){
        _formulario.value = _formulario.value.copy(aceptaReglamento = valor)
        _errores.value = _errores.value.copy(aceptaReglamento = Validadores.reglamento(valor))
    }

    fun guardar(alExito: () -> Unit) {
        if (!validarFormulario()) return
        val f = _formulario.value
        viewModelScope.launch {
            repo.guardar(
                Reserva(
                    vecino = f.vecino.trim(),
                    correo = f.correo.trim(),
                    telefono = f.telefono,
                    horaInicio = f.horaInicio.trim(),
                    horaFin = f.horaFin.trim(),
                    fecha = f.fecha!!,
                    monto = f.monto.toInt(),
                    usaCocina = f.usaCocina
                )
            )
            _formulario.value = ReservaFormulario()
            _errores.value = ReservaErrores()
            alExito()
        }
    }


}