package com.example.evaluacion2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    @Query("SELECT * FROM movimientos ORDER BY id DESC")
    fun listar(): Flow<List<Movimiento>>

    @Insert
    suspend fun insertar(movimiento: Movimiento): Long
}
