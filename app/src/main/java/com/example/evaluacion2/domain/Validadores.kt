package com.example.evaluacion2.domain

object Validadores {

    fun telefono(tel: String): String? = when {
        tel.isBlank() -> "El teléfono es obligatorio"

        !Regex("^9\\d{8}$").matches(tel.trim()) -> "Deben ser 9 dígitos y partir con 9 (ej: 912345678)"

        else -> null
    }
}