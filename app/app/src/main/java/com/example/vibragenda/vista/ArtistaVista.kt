package com.example.vibragenda.vista

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.vibragenda.R
import com.example.vibragenda.modelo.ServicioApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64

class ArtistaVista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artista_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val img = findViewById<ImageView>(R.id.imgVwArtista)
        val nombre = findViewById<TextView>(R.id.txtVwArtistaNombre)
        val desc = findViewById<TextView>(R.id.txtVwArtistaDesc)
        val fecha = findViewById<TextView>(R.id.txtVwArtistaFecha)

        lifecycleScope.launch(Dispatchers.IO) {
            val artista = ServicioApi.obtenerArtista(intent.getIntExtra("ARTISTA_ID", 1))
            withContext(Dispatchers.Main) {
                try {
                    val byteArray = Base64.getDecoder().decode(artista.imagen)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    img.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    img.setImageResource(R.drawable.no_image_18x10)
                }

                nombre.text = artista.nombre
                desc.text = artista.descripcion
                fecha.text = artista.fechaFormacion
            }
        }



    }
}