package com.angelhazmato.vibragenda.controlador

import com.angelhazmato.vibragenda.modelo.entidad.Artista
import com.angelhazmato.vibragenda.modelo.entidad.Evento
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

object ServicioApi {
    lateinit var url: String

    /* ARTISTA */

    fun obtenerArtistas(): ArrayList<Artista> {
        var lista = ArrayList<Artista>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Artista>>() {}.type)
        }
        return lista
    }

    fun obtenerArtista(id: Int): Artista {
        var artista = Artista()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas/$id").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            artista = gson.fromJson(responseData, object : TypeToken<Artista>() {}.type)
        }
        return artista
    }

    fun obtenerArtistasDeEvento(idEvento: Int): List<Artista> {
        var lista = ArrayList<Artista>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas/evento/$idEvento").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Artista>>() {}.type)
        }
        return lista
    }

    fun crearArtista(artista: Artista): Boolean {
        var artistaCreado: Artista? = null
        val client = OkHttpClient()
        val gson = Gson()
        val body = gson.toJson(artista).toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$url/api/artistas")
            .post(body)
            .build()

        client.newCall(request).execute().use {response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            artistaCreado = gson.fromJson(responseData, object : TypeToken<Artista>() {}.type)
            if (artistaCreado != null) return true
        }
        return false
    }

    fun eliminarArtista(id: Int): Boolean {
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/artistas/$id").delete().build()
        client.newCall(request).execute().use { response ->
            return response.isSuccessful
        }
    }

    /* EVENTO */

    fun obtenerEventos(): ArrayList<Evento> {
        var lista = ArrayList<Evento>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Evento>>() {}.type)
        }
        return lista
    }

    fun obtenerEvento(id: Int): Evento {
        var evento = Evento()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos/$id").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            evento = gson.fromJson(responseData, object : TypeToken<Evento>() {}.type)
        }
        return evento
    }

    fun crearEvento(evento: Evento): Boolean {
        var eventoCreado: Evento? = null
        val client = OkHttpClient()
        val gson = Gson()
        val body = gson.toJson(evento).toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("$url/api/eventos")
            .post(body)
            .build()

        client.newCall(request).execute().use {response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            eventoCreado = gson.fromJson(responseData, object : TypeToken<Evento>() {}.type)
            if (eventoCreado != null) return true
        }
        return false
    }

    fun eliminarEvento(id: Int): Boolean {
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos/$id").delete().build()
        client.newCall(request).execute().use { response ->
            return response.isSuccessful
        }
    }

    fun obtenerEventosDeArtista(idArtista: Int): List<Evento> {
        var lista = ArrayList<Evento>()
        val client = OkHttpClient()
        val request = Request.Builder().url("$url/api/eventos/artista/$idArtista").build()
        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseData = response.body!!.string()
            val gson = Gson()
            lista = gson.fromJson(responseData, object : TypeToken<List<Evento>>() {}.type)
        }
        return lista
    }
}