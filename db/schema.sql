CREATE TABLE IF NOT EXISTS artista(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(60) NOT NULL,
	descripcion TEXT,
	fecha_formacion DATE NOT NULL,
	imagen TEXT,
	icono TEXT
);

CREATE TABLE IF NOT EXISTS evento(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	fecha DATE,
	lugar VARCHAR(127),
	imagen TEXT,
	icono TEXT
);

CREATE TABLE IF NOT EXISTS artista_evento(
    id_artista INT,
    id_evento INT,
    PRIMARY KEY (id_artista, id_evento),
    FOREIGN KEY (id_artista) REFERENCES artista(id),
    FOREIGN KEY (id_evento) REFERENCES evento(id)
);

CREATE TABLE IF NOT EXISTS usuario(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL UNIQUE,
	hash_contrasenha VARCHAR(128) NOT NULL,
	salt VARCHAR(64) NOT NULL,
	perfil VARCHAR(7) NOT NULL CHECK (perfil IN('admin', 'usuario'))
);