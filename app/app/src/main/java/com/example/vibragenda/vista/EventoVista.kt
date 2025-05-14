package com.example.vibragenda.vista

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vibragenda.R
import com.example.vibragenda.modelo.ServicioApi
import com.example.vibragenda.vista.adaptador.ArtistaAdaptador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64

class EventoVista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evento_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val img = findViewById<ImageView>(R.id.imgVwEvento)
        val nombre = findViewById<TextView>(R.id.txtVwEventoNombre)
        val fecha = findViewById<TextView>(R.id.txtVwEventoFecha)
        val artistasConfirmados = findViewById<TextView>(R.id.txtVwEventoArtistas)
        val spinner = findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.rvEventoArtistas)

        VistaUtils.ocultarDatos(spinner, img, nombre, fecha, artistasConfirmados, recyclerView)


        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val evento = ServicioApi.obtenerEvento(intent.getIntExtra("EVENTO_ID", 1))
            val artistasEvento = ServicioApi.obtenerArtistasDeEvento(evento.id)
            Log.i("AHM", "Num artistas: ${artistasEvento.size}, evento id: ${evento.id}")
            withContext(Dispatchers.Main) {
                try {
                    val byteArray = Base64.getDecoder().decode(evento.imagen)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    img.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    img.setImageResource(R.drawable.no_image_18x10)
                }
                nombre.text = evento.nombre
                fecha.text = evento.fecha

                if (artistasEvento.isNotEmpty()) {
                    val artistaAdaptador = ArtistaAdaptador(artistasEvento) { artista  ->
                        val intent = Intent(this@EventoVista, ArtistaVista::class.java)
                        intent.putExtra("ARTISTA_ID", artista.id)
                        startActivity(intent)
                    }
                    recyclerView.adapter = artistaAdaptador
                    VistaUtils.mostrarDatos(spinner, img, nombre, fecha, artistasConfirmados, recyclerView)
                } else {
                    artistasConfirmados.text = getString(R.string.no_hay_artistas_confirmados)
                    VistaUtils.mostrarDatos(spinner, img, nombre, fecha, artistasConfirmados)
                }
            }
        }
    }
}