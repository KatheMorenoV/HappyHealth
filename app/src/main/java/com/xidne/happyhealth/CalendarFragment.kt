package com.xidne.happyhealth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CalendarFragment : Fragment() {

    private lateinit var btnReservar: Button
    private lateinit var btnMisCitas: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Inicializa el botón de reservar y establece el listener
        btnReservar = view.findViewById(R.id.btnReservar)
        btnReservar.setOnClickListener {
            val intent = Intent(activity, ReserveActivity::class.java)
            startActivity(intent)
        }

        // Inicializa el botón de mis citas y establece el listener
        btnMisCitas = view.findViewById(R.id.btnMisCitas)
        btnMisCitas.setOnClickListener {
            val citas = Intent(activity, CitasActivity::class.java)
            startActivity(citas)
        }

        return view
    }
}


