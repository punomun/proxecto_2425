package com.angelhazmato.proyecto.servicio.controller;

import com.angelhazmato.proyecto.servicio.entity.Usuario;
import com.angelhazmato.proyecto.servicio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable int id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardar(usuario);

        if (nuevoUsuario == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        URI locationHeader = URI.create("/api/usuarios/" + nuevoUsuario.getId());
        return ResponseEntity.created(locationHeader).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> putUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizar(id, usuario);
        if (usuarioActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        if (usuarioService.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> deleteUsuarioPorNombre(@PathVariable String nombre) {
        if (usuarioService.eliminarPorNombre(nombre)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
