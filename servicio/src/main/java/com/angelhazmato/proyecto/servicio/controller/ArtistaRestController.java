package com.angelhazmato.proyecto.servicio.controller;

import com.angelhazmato.proyecto.servicio.entity.Artista;
import com.angelhazmato.proyecto.servicio.service.ArtistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaRestController {

    private final ArtistaService artistaService;

    public ArtistaRestController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public ResponseEntity<List<Artista>> getArtistas() {
        return ResponseEntity.ok(artistaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> getArtista(@PathVariable int id) {
        Artista artista = artistaService.obtenerPorId(id);
        if (artista == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(artista);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<Artista >> getArtistaByEvento(@PathVariable int idEvento) {
        List<Artista> artistas = artistaService.obtenerPorIdEvento(idEvento);
        if (artistas == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(artistas);
    }

    @PostMapping
    public ResponseEntity<Artista> postArtista(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaService.guardar(artista);
        URI locationHeader = URI.create("/api/artistas/" + nuevoArtista.getId());
        return ResponseEntity.created(locationHeader).body(nuevoArtista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> putArtista(@PathVariable int id, @RequestBody Artista artista) {
        Artista artistaActualizado = artistaService.actualizar(id, artista);
        if (artistaActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(artistaActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtista(@PathVariable int id) {
        if (artistaService.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArtistas() {
        artistaService.eliminarTodos();
        return ResponseEntity.noContent().build();
    }

}
