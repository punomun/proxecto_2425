package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.entity.Artista;

import java.util.List;

public interface ArtistaService {
    List<Artista> obtenerTodos();
    Artista obtenerPorId(int id);
    Artista guardar(Artista artista);
    Artista actualizar(int id, Artista artista);
    boolean eliminarPorId(int id);
	void eliminarTodos();
}
