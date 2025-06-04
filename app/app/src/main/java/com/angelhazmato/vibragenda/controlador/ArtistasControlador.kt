package com.angelhazmato.vibragenda.controlador

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.lifecycle.lifecycleScope
import com.example.vibragenda.R
import com.angelhazmato.vibragenda.vista.entidad.ArtistaVista
import com.angelhazmato.vibragenda.vista.entidad.ArtistasVista
import com.angelhazmato.vibragenda.vista.entidad.EventosVista
import com.angelhazmato.vibragenda.vista.adaptador.ArtistaAdaptador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64

object ArtistasControlador {
	fun cargarListaArtistas(vista: ArtistasVista) {
		vista.lifecycleScope.launch(Dispatchers.IO) {
            val listaArtistas = ServicioApi.obtenerArtistas()
            withContext(Dispatchers.Main) {
                val artistaAdaptador = ArtistaAdaptador(listaArtistas) { artista  ->
                    val intent = Intent(vista, ArtistaVista::class.java)
                    intent.putExtra("ARTISTA_ID", artista.id)
                    vista.startActivity(intent)
                }
                vista.recyclerView.adapter = artistaAdaptador
				
                VistaUtils.mostrarDatos(vista.spinner, vista.recyclerView, vista.toolbar)
            }
        }
	}
	
	fun cargarArtista(vista: ArtistaVista) {
		vista.lifecycleScope.launch(Dispatchers.IO) {
            val artista = ServicioApi.obtenerArtista(vista.intent.getIntExtra("ARTISTA_ID", 1))
            withContext(Dispatchers.Main) {
                try {
                    val byteArray = Base64.getDecoder().decode(artista.imagen)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    vista.img.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    vista.img.setImageResource(R.drawable.no_image_18x10)
                }

                vista.nombre.text = artista.nombre
                vista.desc.text = artista.descripcion
                vista.fecha.text = artista.fechaFormacion

                vista.botonEventos.setOnClickListener {
					val intent = Intent(vista, EventosVista::class.java)
					intent.putExtra("ID_ARTISTA", artista.id)
					vista.startActivity(intent)
				}
				
                VistaUtils.mostrarDatos(vista.spinner, vista.img, vista.nombre, vista.desc, vista.fecha, vista.botonEventos, vista.toolbar)
            }
        }
	}
}