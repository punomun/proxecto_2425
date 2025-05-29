package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.entity.Artista;

import java.util.List;

public interface ArtistaService {
    List<Artista> obtenerTodos();
    Artista obtenerPorId(int id);
    List<Artista> obtenerPorIdEvento(int idEvento);
    Artista guardar(Artista artista);
    Artista actualizar(int id, Artista artista);
    boolean eliminarPorId(int id);
	void eliminarTodos();
	boolean existeActuacion(int idArtista, int idEvento);
	boolean inscribirArtistaEnEvento(int idArtista, int idEvento);
	boolean borrarArtistaDeEvento(int idArtista, int idEvento);
}
