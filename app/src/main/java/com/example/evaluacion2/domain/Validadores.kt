package com.example.evaluacion2.domain

object Validadores {

    private val soloLetras = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü ]+$")

    fun nombre (v:String) : String? = when {
        v.isBlank() -> "El nombre es obligatorio"
        v.trim().length < 3 -> "Debe tener al menos 3 caracteres"
        !soloLetras.matches(v.trim()) -> "Solo letras y espacios"
        else -> null
    }

    fun telefono(tel: String): String? = when {
        tel.isBlank() -> "El teléfono es obligatorio"

        !Regex("^9\\d{8}$").matches(tel.trim()) -> "Deben ser 9 dígitos y partir con 9 (ej: 912345678)"

        else -> null
    }
}