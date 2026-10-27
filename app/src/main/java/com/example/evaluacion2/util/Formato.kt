package com.example.evaluacion2.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Int.comoPesos(): String {
    val s = String.format(Locale.US, "%,d", kotlin.math.abs(this)).replace(',', '.')
    return (if (this < 0) "-\$" else "\$") + s
}


fun fechaHoy(): String =
    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
