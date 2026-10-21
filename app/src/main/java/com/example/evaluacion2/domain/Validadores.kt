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
    fun correo(v: String): String? = when {
        v.isBlank() -> "El correo es obligatorio"
        !Regex("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$").matches(v.trim()) -> "Correo no válido (ej: nombre@dominio.cl)"
        else -> null
    }
    private val formatoHora = Regex("^([01]\\d|2[0-3]):[0-5]\\d$")

    fun hora(v: String): String? = when {
        v.isBlank() -> "La hora es obligatoria"
        !formatoHora.matches(v.trim()) -> "Formato HH:mm (ej: 18:30)"
        else -> null
    }

    private fun aMinutos(h: String): Int {
        val partes = h.trim().split(":")
        return partes[0].toInt() * 60 + partes[1].toInt()
    }

    fun rangoHoras(inicio: String, fin: String): String? {
        hora(fin)?.let { return it }
        if (hora(inicio) != null) return null
        return if (aMinutos(fin) <= aMinutos(inicio)) "Debe ser posterior a la hora de inicio" else null
    }
    fun monto(v: String): String? = when {
        v.isBlank() -> "El monto es obligatorio"
        v.toIntOrNull() == null -> "Solo números"
        v.toInt() < 1000 -> "El monto mínimo es $1.000"
        v.toInt() > 500000 -> "El monto máximo es $500.000"
        else -> null
    }
    private const val MS_POR_DIA = 86_400_000L

    private fun hoyEnDias(): Long {
        val ahora = System.currentTimeMillis()
        val desfase = java.util.TimeZone.getDefault().getOffset(ahora)
        return (ahora + desfase) / MS_POR_DIA
    }

    fun fecha(v: Long?): String? = when {
        v == null -> "Debes elegir una fecha"
        v / MS_POR_DIA < hoyEnDias() -> "La fecha no puede ser anterior a hoy"
        else -> null
    }
    fun reglamento(acepta: Boolean): String? =
        if (!acepta) "Debes aceptar el reglamento" else null
}