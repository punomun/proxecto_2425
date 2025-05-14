package com.example.vibragenda.vista.adaptador

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.modelo.entidad.Evento
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vibragenda.R
import java.util.Base64

class EventoAdaptador(private val eventoList: List<Evento>, private val onItemClick: (Evento) -> Unit) : RecyclerView.Adapter<EventoAdaptador.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombre: TextView = view.findViewById(R.id.txtViewEventoItem)
        var imagen: ImageView = view.findViewById(R.id.imgEventoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento = eventoList[position]

        holder.nombre.text = evento.nombre

        try {
            val byteArray = Base64.getDecoder().decode(evento.icono)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            holder.imagen.setImageBitmap(bitmap)
        } catch (e: Exception) {
            holder.imagen.setImageResource(R.drawable.no_image_1x1)
        }

        holder.itemView.setOnClickListener {
            onItemClick(evento)
        }
    }

    override fun getItemCount(): Int {
        return eventoList.size
    }

}