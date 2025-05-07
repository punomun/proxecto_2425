package com.angelhazmato.proyecto.servicio.dao;

import com.angelhazmato.proyecto.servicio.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
