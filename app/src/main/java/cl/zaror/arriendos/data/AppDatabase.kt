package cl.zaror.arriendos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// OJO: incluye Reserva (de Matias). Los nombres y campos deben coincidir con su entidad.
@Database(entities = [Movimiento::class, Reserva::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movimientoDao(): MovimientoDao
    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile private var instancia: AppDatabase? = null

        fun obtener(context: Context): AppDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "zaror.db"
                ).build().also { instancia = it }
            }
    }
}
