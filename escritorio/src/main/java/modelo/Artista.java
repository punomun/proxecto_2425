package modelo;

import java.util.HashSet;
import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Artista {
    private int id;
    private String nombre;
    private String descripcion;
    private String fechaFormacion;
    private String imagen;
    private String icono;
    private Set<Evento> eventos = new HashSet<>();
	
    public Artista(String nombre, String fechaFormacion) {
	this.nombre = nombre;
	this.fechaFormacion = fechaFormacion;
    }

    public Artista(String nombre, String desc, String fechaFormacion, String imagen, String icono) {
        this.nombre = nombre;
        this.descripcion = desc;
        this.fechaFormacion = fechaFormacion;
        this.imagen = imagen;
        this.icono = icono;
    }  
}