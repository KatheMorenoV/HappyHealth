package com.xidne.happyhealth

import androidx.room.Entity
import androidx.room.PrimaryKey

//Representa una cita en la base de datos.

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // ID único de la cita
    val descripcion: String,  // Descripción de la cita
    val especialidad: String,  // Especialidad médica relacionada
    val doctor: String,  // Nombre del doctor asignado
    val fecha: String  // Fecha de la cita
)

