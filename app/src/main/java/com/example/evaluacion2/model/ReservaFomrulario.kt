package com.example.evaluacion2.model

data class ReservaFomrulario(
    val vecino: String ="",
    val telefono: String = "")

data class ReservaErrores(
    val vecino: String? = null,
    val telefono: String? = null)