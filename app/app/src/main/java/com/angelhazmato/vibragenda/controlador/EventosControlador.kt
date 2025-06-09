package com.angelhazmato.vibragenda.controlador

import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.angelhazmato.vibragenda.modelo.entidad.Evento
import com.angelhazmato.vibragenda.vista.entidad.ArtistaVista
import com.angelhazmato.vibragenda.vista.entidad.EventoVista
import com.angelhazmato.vibragenda.vista.entidad.EventosVista
import com.angelhazmato.vibragenda.vista.adaptador.ArtistaAdaptador
import com.angelhazmato.vibragenda.vista.adaptador.EventoAdaptador
import com.example.vibragenda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64

object EventosControlador {
    fun cargarListaEventos(vista: EventosVista) {
        vista.lifecycleScope.launch(Dispatchers.IO) {
            val idArtista = vista.intent.getIntExtra("ID_ARTISTA", -1)
            val listaEventos: List<Evento> = if (idArtista == -1) ServicioApi.obtenerEventos()
            else ServicioApi.obtenerEventosDeArtista(idArtista)

            if (listaEventos.isEmpty()) {
                val str = if (idArtista == -1) vista.getString(R.string.no_hay_eventos)
                else vista.getString(R.string.artista_no_eventos)
                withContext(Dispatchers.Main) {
                    Toast.makeText(vista, str, Toast.LENGTH_LONG).show()
                    vista.finish()
                }
                return@launch
            }

            withContext(Dispatchers.Main) {
                val eventoAdaptador = EventoAdaptador(listaEventos) { evento ->
                    val intent = Intent(vista, EventoVista::class.java)
                    intent.putExtra("EVENTO_ID", evento.id)
                    vista.startActivity(intent)
                }
                vista.recyclerView.adapter = eventoAdaptador

                VistaUtils.mostrarDatos(vista.spinner, vista.recyclerView, vista.toolbar)
            }
        }
    }

    fun cargarEvento(vista: EventoVista) {
        vista.lifecycleScope.launch(Dispatchers.IO) {
            val evento = ServicioApi.obtenerEvento(vista.intent.getIntExtra("EVENTO_ID", 1))
            val artistasEvento = ServicioApi.obtenerArtistasDeEvento(evento.id)
            withContext(Dispatchers.Main) {
                try {
                    val byteArray = Base64.getDecoder().decode(evento.imagen)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    vista.img.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    vista.img.setImageResource(R.drawable.no_image_18x10)
                }
                vista.nombre.text = evento.nombre
                vista.fecha.text = evento.fecha
                vista.lugar.text = evento.lugar

                if (artistasEvento.isNotEmpty()) {
                    val artistaAdaptador = ArtistaAdaptador(artistasEvento) { artista ->
                        val intent = Intent(vista, ArtistaVista::class.java)
                        intent.putExtra("ARTISTA_ID", artista.id)
                        vista.startActivity(intent)
                    }
                    vista.recyclerView.adapter = artistaAdaptador

                    VistaUtils.mostrarDatos(
                        vista.spinner,
                        vista.img,
                        vista.nombre,
                        vista.fecha,
                        vista.lugar,
                        vista.artistasConfirmados,
                        vista.recyclerView,
                        vista.toolbar
                    )
                } else {
                    vista.artistasConfirmados.text =
                        vista.getString(R.string.no_hay_artistas_confirmados)
                    VistaUtils.mostrarDatos(
                        vista.spinner,
                        vista.img,
                        vista.nombre,
                        vista.fecha,
                        vista.lugar,
                        vista.artistasConfirmados,
                        vista.toolbar
                    )
                }
            }
        }
    }
}