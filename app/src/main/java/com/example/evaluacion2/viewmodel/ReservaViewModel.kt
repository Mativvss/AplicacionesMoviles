package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.evaluacion2.domain.Validadores
import com.example.evaluacion2.model.ReservaErrores
import com.example.evaluacion2.model.ReservaFomrulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReservaViewModel: ViewModel() {
    private val _formulario = MutableStateFlow(ReservaFomrulario())
    val formulario: StateFlow<ReservaFomrulario> = _formulario.asStateFlow()

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
            monto = Validadores.monto(f.monto))
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
}