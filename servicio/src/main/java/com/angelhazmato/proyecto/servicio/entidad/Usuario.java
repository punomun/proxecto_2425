package com.angelhazmato.proyecto.servicio.entidad;


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
	private Perfil perfil;

    @Getter
    enum Perfil {
        ADMIN("admin"),
        USUARIO("usuario");

        private final String value;

        Perfil(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public Usuario(String nombre, String contraseña, Perfil perfil) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.perfil = perfil;
    }
}
