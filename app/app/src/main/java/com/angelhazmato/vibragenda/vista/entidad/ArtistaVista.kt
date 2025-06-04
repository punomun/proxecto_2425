package com.angelhazmato.vibragenda.vista.entidad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.vibragenda.R
import com.angelhazmato.vibragenda.controlador.ArtistasControlador
import com.angelhazmato.vibragenda.controlador.ServicioApi
import com.angelhazmato.vibragenda.vista.AjustesVista
import com.angelhazmato.vibragenda.controlador.VistaUtils
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistaVista : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var nombre: TextView
    lateinit var desc: TextView
    lateinit var fecha: TextView
    lateinit var botonEventos: Button
    lateinit var spinner: ProgressBar
    lateinit var toolbar: MaterialToolbar
    private var isFavorito = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artista_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val idArtista = intent.getIntExtra("ARTISTA_ID", -1)

        toolbar = findViewById(R.id.artistaToolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorito_toolbar -> {
                    Toast.makeText(this@ArtistaVista, "No implementado", Toast.LENGTH_SHORT).show()
                    if (isFavorito) {
                        it.setIcon(R.drawable.ic_star_24px)
                        isFavorito = false
                    } else {
                        it.setIcon(R.drawable.ic_star_filled_24px)
                        isFavorito = true
                    }
                    true
                }

                R.id.editar_toolbar -> {
                    Toast.makeText(this@ArtistaVista, "No implementado", Toast.LENGTH_LONG).show()
                    true
                }

                R.id.borrar_toolbar -> {
                    AlertDialog.Builder(this@ArtistaVista).setTitle("Borrar artista")
                        .setMessage("¿Estás seguro que deseas borrar este artista?")
                        .setPositiveButton("Sí") { dialogInterface, _ ->
                            lifecycleScope.launch(Dispatchers.IO) {
                                val borrado = ServicioApi.eliminarArtista(idArtista)
                                if (borrado) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(this@ArtistaVista, "Artista borrado", Toast.LENGTH_LONG).show()
                                        dialogInterface.dismiss()
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(this@ArtistaVista, "El artista no se ha podido borrar", Toast.LENGTH_LONG).show()
                                }
                            }

                        }.setNegativeButton("No") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        }.create().show()
                    true
                }

                R.id.ajustes_toolbar -> {
                    startActivity(Intent(this@ArtistaVista, AjustesVista::class.java))
                    true
                }

                else -> false
            }
        }

        img = findViewById(R.id.imgVwArtista)
        nombre = findViewById(R.id.txtVwArtistaNombre)
        desc = findViewById(R.id.txtVwArtistaDesc)
        fecha = findViewById(R.id.txtVwArtistaFecha)
        botonEventos = findViewById(R.id.btnArtistaBuscarEventos)
        spinner = findViewById(R.id.progressBar)

        VistaUtils.ocultarDatos(spinner, img, nombre, desc, fecha, botonEventos, toolbar)

        ArtistasControlador.cargarArtista(this)
    }
}