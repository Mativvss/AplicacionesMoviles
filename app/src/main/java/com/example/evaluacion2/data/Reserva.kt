package com.example.evaluacion2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class EstadoReserva { PENDIENTE, CONFIRMADA, CANCELADA }

@Entity(tableName = "reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vecino: String,
    val correo: String,
    val telefono: String,
    val horaInicio: String,
    val horaFin: String,
    val fecha: Long,
    val monto: Int,
    val usaCocina: Boolean,
    val estado: EstadoReserva = EstadoReserva.PENDIENTE
)