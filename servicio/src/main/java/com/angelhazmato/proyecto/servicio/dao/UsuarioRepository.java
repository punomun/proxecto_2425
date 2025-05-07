package com.angelhazmato.proyecto.servicio.dao;

import com.angelhazmato.proyecto.servicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    void deleteByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
