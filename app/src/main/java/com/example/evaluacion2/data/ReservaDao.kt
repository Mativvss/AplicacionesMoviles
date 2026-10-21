package com.example.evaluacion2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Insert
    suspend fun insertar(reserva: Reserva)

    @Query("SELECT * FROM reservas ORDER BY fecha ASC")
    fun observarTodas(): Flow<List<Reserva>>

    @Query("UPDATE reservas SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Long, estado: EstadoReserva)
}