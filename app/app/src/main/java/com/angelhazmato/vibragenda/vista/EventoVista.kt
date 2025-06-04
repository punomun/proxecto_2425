package com.angelhazmato.vibragenda.vista

import android.content.Intent
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angelhazmato.vibragenda.controlador.EventosControlador
import com.angelhazmato.vibragenda.controlador.ServicioApi
import com.example.vibragenda.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventoVista : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var nombre: TextView
    lateinit var fecha: TextView
    lateinit var lugar: TextView
    lateinit var artistasConfirmados: TextView
    lateinit var spinner: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: MaterialToolbar
    private var isFavorito = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evento_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val idEvento = intent.getIntExtra("EVENTO_ID", -1)

        toolbar = findViewById(R.id.eventoToolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorito_toolbar -> {
                    Toast.makeText(this@EventoVista, "No implementado", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@EventoVista, "No implementado", Toast.LENGTH_LONG).show()
                    true
                }

                R.id.borrar_toolbar -> {
                    AlertDialog.Builder(this@EventoVista).setTitle("Borrar evento")
                        .setMessage("¿Estás seguro que deseas borrar este evento?")
                        .setPositiveButton("Sí") { dialogInterface, _ ->
                            lifecycleScope.launch(Dispatchers.IO) {
                                val borrado = ServicioApi.eliminarEvento(idEvento)
                                if (borrado) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@EventoVista,
                                            "Evento borrado",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        dialogInterface.dismiss()
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(
                                        this@EventoVista,
                                        "El evento no se ha podido borrar",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        }.setNegativeButton("No") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        }.create().show()
                    true
                }

                R.id.ajustes_toolbar -> {
                    startActivity(Intent(this@EventoVista, AjustesVista::class.java))
                    true
                }

                else -> false
            }
        }

        img = findViewById(R.id.imgVwEvento)
        nombre = findViewById(R.id.txtVwEventoNombre)
        fecha = findViewById(R.id.txtVwEventoFecha)
        lugar = findViewById(R.id.txtVwEventoLugar)
        artistasConfirmados = findViewById(R.id.txtVwEventoArtistas)
        spinner = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rvEventoArtistas)

        VistaUtils.ocultarDatos(
            spinner,
            img,
            nombre,
            fecha,
            lugar,
            artistasConfirmados,
            recyclerView,
            toolbar
        )

        recyclerView.layoutManager = LinearLayoutManager(this)

        EventosControlador.cargarEvento(this)
    }
}