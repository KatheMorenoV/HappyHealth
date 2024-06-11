package com.xidne.happyhealth

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var citasAdapter: CitasAdapter
    private lateinit var citas: MutableList<Cita>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Solo cargar citas si el usuario está autenticado
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            loadCitas()
        }
    }

    private fun loadCitas() {
        lifecycleScope.launch(Dispatchers.IO) {
            // Cargar citas desde la base de datos
            citas = AppDatabase.getDatabase(applicationContext).citaDao().getAll().toMutableList()
            withContext(Dispatchers.Main) {
                // Configurar el adaptador del RecyclerView
                citasAdapter = CitasAdapter(citas, this@CitasActivity::deleteCita)
                recyclerView.adapter = citasAdapter
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteCita(cita: Cita) {
        // Mostrar diálogo de confirmación para eliminar la cita
        AlertDialog.Builder(this)
            .setTitle("Eliminar Cita")
            .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
            .setPositiveButton("Sí") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    // Eliminar cita de la base de datos
                    AppDatabase.getDatabase(applicationContext).citaDao().delete(cita)
                    withContext(Dispatchers.Main) {
                        // Actualizar la lista y notificar al adaptador
                        citas.remove(cita)
                        citasAdapter.notifyDataSetChanged()
                        Toast.makeText(this@CitasActivity, "Cita eliminada", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}



