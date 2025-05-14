package com.example.vibragenda.vista.adaptador

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.modelo.entidad.Artista
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vibragenda.R
import java.util.Base64

class ArtistaAdaptador(private val artistaList: List<Artista>, private val onItemClick: (Artista) -> Unit) : RecyclerView.Adapter<ArtistaAdaptador.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombre: TextView = view.findViewById(R.id.txtViewArtistaItem)
        var imagen: ImageView = view.findViewById(R.id.imgArtistaItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_artista, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artista = artistaList[position]

        holder.nombre.text = artista.nombre

        try {
            val byteArray = Base64.getDecoder().decode(artista.icono)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            holder.imagen.setImageBitmap(bitmap)
        } catch(e: Exception) {
            holder.imagen.setImageResource(R.drawable.no_image_1x1)
        }

        holder.itemView.setOnClickListener {
            onItemClick(artista)
        }
    }

    override fun getItemCount(): Int {
        return artistaList.size
    }

}