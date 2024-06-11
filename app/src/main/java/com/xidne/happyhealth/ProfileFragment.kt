package com.xidne.happyhealth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private lateinit var btnCerrarSesion: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Obtener la referencia al botón
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion)

        // Configurar el listener para el botón
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        return view
    }

    private fun cerrarSesion() {
        // Lógica para borrar las citas de la base de datos local
        GlobalScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(requireContext().applicationContext).citaDao().deleteAll()
            withContext(Dispatchers.Main) {
                // Lógica para cerrar sesión
                FirebaseAuth.getInstance().signOut()
                // Redirigir a la actividad de inicio de sesión o realizar otra acción
                val intent = Intent(activity, MainActivity::class.java)
                Toast.makeText(activity, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                activity?.finish() // Opcional: finalizar la actividad actual para que no se pueda volver con el botón de atrás
            }
        }
    }
}
