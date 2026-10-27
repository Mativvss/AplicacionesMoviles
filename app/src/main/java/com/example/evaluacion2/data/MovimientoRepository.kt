package com.example.evaluacion2.data

class MovimientoRepository(private val dao: MovimientoDao) {
    val movimientos = dao.listar()
    suspend fun insertar(movimiento: Movimiento) = dao.insertar(movimiento)
}
