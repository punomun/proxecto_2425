CREATE TABLE IF NOT EXISTS artista(
	id INT PRIMARY KEY,
	nombre VARCHAR(60) NOT NULL,
	descripcion VARCHAR(5000),
	fecha_formacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS evento(
	id INT PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	fecha DATE,
	lugar VARCHAR(127)
);

CREATE TABLE IF NOT EXISTS artista_evento(
    id_artista INT,
    id_evento INT,
    PRIMARY KEY (id_artista, id_evento),
    FOREIGN KEY (id_artista) REFERENCES artista(id),
    FOREIGN KEY (id_evento) REFERENCES evento(id)
);

CREATE TABLE IF NOT EXISTS usuario(
	id INT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL UNIQUE,
	contraseña VARCHAR(80) NOT NULL,
	perfil VARCHAR(7) NOT NULL CHECK (perfil IN('admin', 'usuario'))
);