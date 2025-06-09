package com.angelhazmato.vibragenda.vista.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angelhazmato.vibragenda.controlador.ServicioApi
import com.angelhazmato.vibragenda.modelo.entidad.Evento
import com.angelhazmato.vibragenda.vista.AjustesVista
import com.example.vibragenda.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale

class GestionarEvento : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private var fecha: String? = null
    private lateinit var btnFecha: Button
    private lateinit var textViewFecha: TextView
    private lateinit var btnCrear: Button
    private lateinit var btnImg: Button
    private lateinit var btnIco: Button
    private var imgBase64: String? = null
    private var icoBase64: String? = null
    private lateinit var imagenOIcono: String
    private val escogerImagen = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        if (it != null) {
            val mimeType = contentResolver.getType(it)

            if (mimeType != null && mimeType.startsWith("image/")) {
                val base64: String? = try {
                    String(
                        Base64.getEncoder().encode(contentResolver.openInputStream(it)?.readBytes())
                    )
                } catch (e: Exception) {
                    null
                }
                if ("IMG" == imagenOIcono) imgBase64 = base64.toString()
                else icoBase64 = base64.toString()
            } else {
                Toast.makeText(
                    this@GestionarEvento,
                    getString(R.string.tipo_archivo_imagen),
                    Toast.LENGTH_SHORT
                ).show()
                return@registerForActivityResult
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestionar_evento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.gestionarEventoToolbar)
        btnCrear = findViewById(R.id.btnGestionEveConfirmar)
        btnFecha = findViewById(R.id.btnGestionEveFecha)
        textViewFecha = findViewById(R.id.tvEveFechaSeleccionada)
        btnImg = findViewById(R.id.btnGestionEveImg)
        btnIco = findViewById(R.id.btnGestionEveIco)

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string.selecciona_fecha))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        btnFecha.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
                textViewFecha.text =getString(R.string.fecha_seleccionada, fecha)
            }
        }

        btnImg.setOnClickListener {
            imagenOIcono = "IMG"
            escogerImagen.launch(arrayOf("image/*"))
        }

        btnIco.setOnClickListener {
            imagenOIcono = "ICO"
            escogerImagen.launch(arrayOf("image/*"))
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ajustes_toolbar -> {
                    startActivity(Intent(this@GestionarEvento, AjustesVista::class.java))
                    true
                }

                else -> false
            }
        }

        cargarCrear()
    }

    private fun cargarCrear() {
        toolbar.title = "Crear evento"
        btnCrear.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etGestionEveNombre).text.trim().toString()
            if (nombre.isBlank()) {
                Toast.makeText(this@GestionarEvento, getString(R.string.falta_nombre), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val evento = Evento(
                0,
                nombre,
                fecha.toString(),
                findViewById<EditText>(R.id.etGestionEveLugar).text.trim().toString(),
                imgBase64,
                icoBase64
            )
            lifecycleScope.launch(Dispatchers.IO) {
                val creado = ServicioApi.crearEvento(evento)
                withContext(Dispatchers.Main) {
                    if (creado) {
                        Toast.makeText(
                            this@GestionarEvento, "Evento creado correctamente", Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@GestionarEvento,
                            getString(R.string.error_crear_evento),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}