package modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Evento {

    private int id;
    private String nombre;
    private String fecha;
    private String lugar;
    private String imagen;
    private String icono;
    private Set<Artista> artistas = new HashSet<>();

    public Evento(String nombre) {
        this.nombre = nombre;
    }

    public Evento(String nombre, String fecha, String lugar, String imagen, String icono) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.imagen = imagen;
        this.icono = icono;
    }
}
