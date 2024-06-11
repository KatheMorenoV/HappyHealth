package com.xidne.happyhealth

import androidx.room.*

@Dao
interface CitaDao {

    //Obtiene todas las citas de la base de datos.
    @Query("SELECT * FROM citas")
    fun getAll(): List<Cita>

    //Inserta una nueva cita en la base de datos.
    @Insert
    fun insert(cita: Cita)

    //Actualiza una cita existente en la base de datos.
    @Update
    fun update(cita: Cita)

    //Elimina una cita de la base de datos.
    @Delete
    fun delete(cita: Cita)

    //Elimina todas las citas de la base de datos
    @Query("DELETE FROM citas")
    fun deleteAll()
}
