package com.example.vibragenda.modelo

import com.example.vibragenda.modelo.entidad.Artista
import com.example.vibragenda.modelo.entidad.Evento
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object ServicioApi {
    lateinit var url: String
    fun obtenerArtistas(): ArrayList<Artista> {
        var lista = ArrayList<Artista>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body()!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Artista>>() {}.type)
        }
        return lista
    }

    fun obtenerEventos(): ArrayList<Evento> {
        var lista = ArrayList<Evento>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body()!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Evento>>() {}.type)
        }
        return lista
    }

    fun obtenerArtista(id: Int): Artista {
        var artista = Artista()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas/$id").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body()!!.string()
            val gson = Gson()
            artista = gson.fromJson(responseData, object : TypeToken<Artista>() {}.type)
        }
        return artista
    }

    fun obtenerEvento(id: Int): Evento {
        var evento = Evento()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos/$id").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body()!!.string()
            val gson = Gson()
            evento = gson.fromJson(responseData, object : TypeToken<Evento>() {}.type)
        }
        return evento
    }
}