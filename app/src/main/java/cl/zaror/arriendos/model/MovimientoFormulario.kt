package cl.zaror.arriendos.model

import cl.zaror.arriendos.data.TipoMovimiento

val categoriasGasto = listOf("Luz", "Agua", "Gas", "Mantención", "Otros")
val categoriasIngreso = listOf("Arriendo", "Cuota", "Otros")

/** Estado completo del formulario de movimientos (incluye mensajes de error por campo). */
data class MovimientoFormulario(
    val tipo: TipoMovimiento = TipoMovimiento.GASTO,
    val categoria: String = categoriasGasto.first(),
    val monto: String = "",
    val descripcion: String = "",
    val comprobanteUri: String? = null,
    val errorMonto: String? = null,
    val errorDescripcion: String? = null
) {
    val categorias: List<String>
        get() = if (tipo == TipoMovimiento.GASTO) categoriasGasto else categoriasIngreso
}
