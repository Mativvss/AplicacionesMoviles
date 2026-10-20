package cl.zaror.arriendos.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Muestra un entero como pesos chilenos: 20000 -> $20.000 */
fun Int.comoPesos(): String {
    val s = String.format(Locale.US, "%,d", kotlin.math.abs(this)).replace(',', '.')
    return (if (this < 0) "-\$" else "\$") + s
}

/** Fecha de hoy con formato dd/MM/aaaa */
fun fechaHoy(): String =
    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
