package com.xidne.happyhealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * AppDatabase es la clase principal de la base de datos de la aplicación.
 * Esta clase extiende RoomDatabase y proporciona acceso a los DAOs (Data Access Objects).
 * Aquí se define la estructura de la base de datos y las entidades que contiene.
 */

@Database(entities = [Cita::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citaDao(): CitaDao

    companion object {
        // La instancia de la base de datos, marcada como @Volatile para asegurar
        // que todas las escrituras sean visibles a otros hilos inmediatamente.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si INSTANCE es nula, se sincroniza para asegurar que solo un hilo puede acceder a este bloque a la vez.
            return INSTANCE ?: synchronized(this) {
                // Se construye una nueva instancia de la base de datos usando Room.databaseBuilder.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citas_database"
                ).build()
                // Se asigna la instancia construida a INSTANCE.
                INSTANCE = instance
                // Se devuelve la instancia.
                instance
            }
        }
    }
}
