package com.xidne.happyhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitasAdapter(
    private val citas: List<Cita>,
    private val onDelete: (Cita) -> Unit
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view, onDelete)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.bind(cita)
    }

    override fun getItemCount(): Int = citas.size

    class CitaViewHolder(
        itemView: View,
        private val onDelete: (Cita) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val tvEspecialidad: TextView = itemView.findViewById(R.id.tvEspecialidad)
        private val tvDoctor: TextView = itemView.findViewById(R.id.tvDoctor)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)

        fun bind(cita: Cita) {
            // Configurar los campos de texto con los datos de la cita
            tvDescripcion.text = cita.descripcion
            tvEspecialidad.text = cita.especialidad
            tvDoctor.text = cita.doctor
            tvFecha.text = cita.fecha

            // Configurar el PopupMenu para eliminar citas
            itemView.setOnClickListener {
                val popup = PopupMenu(itemView.context, itemView)
                popup.inflate(R.menu.menu_cita)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            onDelete(cita)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }
}





