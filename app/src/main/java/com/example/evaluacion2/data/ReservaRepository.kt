package com.example.evaluacion2.data

import kotlinx.coroutines.flow.Flow

class ReservaRepository(private val dao: ReservaDao) {

    val reservas: Flow<List<Reserva>> = dao.observarTodas()

    suspend fun guardar(reserva: Reserva) = dao.insertar(reserva)

    suspend fun cambiarEstado(id: Long, estado: EstadoReserva) =
        dao.actualizarEstado(id, estado)
}