package com.angelhazmato.proyecto.servicio.service;

import com.angelhazmato.proyecto.servicio.controller.PwdUtils;
import com.angelhazmato.proyecto.servicio.dao.UsuarioRepository;
import com.angelhazmato.proyecto.servicio.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        try {
            String contrasenhaSimple = usuario.getHashContrasenha();
            String salt = PwdUtils.salt();
            String hashContrasenha = PwdUtils.hash(contrasenhaSimple, salt);
            usuario.setSalt(salt);
            usuario.setHashContrasenha(hashContrasenha);
        } catch (Exception e) {
            return null;
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(int id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            try {
                String contrasenhaSimple = usuario.getHashContrasenha();
                String salt = PwdUtils.salt();
                String hashContrasenha = PwdUtils.hash(contrasenhaSimple, salt);
                usuario.setSalt(salt);
                usuario.setHashContrasenha(hashContrasenha);
                usuario.setId(id);
                return usuarioRepository.save(usuario);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean eliminarPorId(int id) {
        if (id == 1 || !usuarioRepository.existsById(id)) return false;
        usuarioRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean eliminarPorNombre(String nombre) {
        if (nombre == null || !usuarioRepository.existsByNombre(nombre)) return false;
        usuarioRepository.deleteByNombre(nombre);
        return true;
    }
}
