package com.example.evaluacion2.model

data class ReservaFomrulario(
    val vecino: String ="",
    val correo: String ="",
    val telefono: String = "",
    val horaInicio: String ="",
    val horaFin : String = "",
    val monto: String = "")


data class ReservaErrores(
    val vecino: String? = null,
    val correo : String? = null,
    val telefono: String? = null,
    val horaInicio: String? =null,
    val horaFin : String? = null,
    val monto : String? = null)