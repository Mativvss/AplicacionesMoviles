package cl.zaror.arriendos.data

class MovimientoRepository(private val dao: MovimientoDao) {
    val movimientos = dao.listar()

    suspend fun insertar(movimiento: Movimiento) = dao.insertar(movimiento)
}
