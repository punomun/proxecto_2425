package com.angelhazmato.proyecto.servicio.entidad;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="artista")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
	@Column(name="descripcion")
	private String descripcion;
    @Column(name="fecha_formacion")
    private LocalDate fechaFormacion;
	@Column(name="imagen")
	private String imagen;
	@Column(name="icono")
	private String icono;
	
	public Artista(String nombre, String descripcion, LocalDate fechaFormacion, String imagen, String icono) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaFormacion = fechaFormacion;
		this.imagen = imagen;
		this.icono = icono;
	}
}
