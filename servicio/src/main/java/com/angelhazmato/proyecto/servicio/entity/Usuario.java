package com.angelhazmato.proyecto.servicio.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
	@Column(name="contraseña")
	private String contraseña;
    @Column(name="perfil")
	private String perfil;

    public Usuario(String nombre, String contraseña, String perfil) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        if (!"admin".equalsIgnoreCase(perfil) && !"usuario".equalsIgnoreCase(perfil)) perfil = "usuario";
        this.perfil = perfil;
    }
}
