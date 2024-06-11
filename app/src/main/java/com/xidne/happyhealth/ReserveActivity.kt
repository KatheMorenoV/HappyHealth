package com.xidne.happyhealth

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReserveActivity : AppCompatActivity() {

    private val selectedCalendar = Calendar.getInstance()
    private lateinit var especialidad: Spinner
    private lateinit var doctorSpinner: Spinner
    private lateinit var cvSiguiente: CardView
    private lateinit var cvConfirmar: CardView
    private lateinit var btnSiguiente: Button
    private lateinit var btnConfirmar: Button
    private lateinit var etDescripcion: EditText
    private lateinit var etFecha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        InicializarVariables()

        // Configura los spinners con opciones
        val optionsEspecialities = arrayOf("General", "Cardiología", "Especialistas")
        especialidad.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, optionsEspecialities)

        val optionsDoctorSpinner = arrayOf("Katherine Rojas", "Kevin Benalcazar", "Manuela Beltrán", "Sofía López", "Arturo Vidal")
        doctorSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, optionsDoctorSpinner)

        btnSiguiente.setOnClickListener {
            cvSiguiente.visibility = View.GONE
            cvConfirmar.visibility = View.VISIBLE
        }

        btnConfirmar.setOnClickListener {
            val descripcion = etDescripcion.text.toString()
            val especialidadSeleccionada = especialidad.selectedItem.toString()
            val doctorSeleccionado = doctorSpinner.selectedItem.toString()
            val fecha = etFecha.text.toString()

            if (descripcion.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Por favor, rellene todos los campos requeridos.", Toast.LENGTH_LONG).show()
            } else {
                val cita = Cita(descripcion = descripcion, especialidad = especialidadSeleccionada, doctor = doctorSeleccionado, fecha = fecha)
                lifecycleScope.launch(Dispatchers.IO) {
                    // Inserta la cita en la base de datos
                    AppDatabase.getDatabase(applicationContext).citaDao().insert(cita)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Cita programada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    // Inicializa las variables de la interfaz
    private fun InicializarVariables() {
        especialidad = findViewById(R.id.especialidad)
        doctorSpinner = findViewById(R.id.doctorSpinner)
        cvSiguiente = findViewById(R.id.cvSiguiente)
        cvConfirmar = findViewById(R.id.cvConfirmar)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        etDescripcion = findViewById(R.id.etDescripcion)
        etFecha = findViewById(R.id.etFecha)
    }

    // Método para mostrar el DatePicker y seleccionar una fecha
    fun onCLickScheduleDate(v: View?) {
        val etScheduleDate = findViewById<EditText>(R.id.etFecha)
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            selectedCalendar.set(y, m, d)
            etScheduleDate.setText("$y-${m + 1}-$d")
        }

        DatePickerDialog(this, listener, year, month, dayOfMonth).show()
    }
}



