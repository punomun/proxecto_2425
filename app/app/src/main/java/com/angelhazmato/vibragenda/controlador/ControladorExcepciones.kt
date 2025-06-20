package com.angelhazmato.vibragenda.controlador

import android.content.Context
import android.content.Intent
import com.angelhazmato.vibragenda.vista.ExcepcionActivity
import java.io.IOException

class ControladorExcepciones(private val applicationContext: Context) :
    Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        if (throwable is IOException) {
            val intent = Intent(applicationContext, ExcepcionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        }
    }
}