package cl.zaror.arriendos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TipoMovimiento { INGRESO, GASTO }

@Entity(tableName = "movimientos")
data class Movimiento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipo: TipoMovimiento,
    val categoria: String,
    val descripcion: String,
    val monto: Int,
    val fecha: String,
    val comprobanteUri: String? = null
)
