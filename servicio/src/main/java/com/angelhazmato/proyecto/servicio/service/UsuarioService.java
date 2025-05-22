package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(int id);
    Usuario guardar(Usuario usuario);
    boolean eliminarPorId(int id);
	boolean eliminarPorNombre(String nombre);
}
