CREATE TABLE estados(
    id INT AUTO_INCREMENT,
    nome VARCHAR(40) UNIQUE,
    codigo INT UNIQUE,
    sigla CHAR(2) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE cidades(
    id INT AUTO_INCREMENT ,
    estado_id INT,
    localidade_id INT DEFAULT NULL,
    nome VARCHAR(75),
    PRIMARY KEY(id),
    FOREIGN KEY(estado_id) REFERENCES estados(id)
);
ALTER TABLE `cidades` ADD UNIQUE `uq_cidUf`(estado_id, nome);

CREATE TABLE usuarios(
    id INT AUTO_INCREMENT,
    login VARCHAR(30) UNIQUE NOT NULL,
    email VARCHAR(70) UNIQUE NOT NULL,
    passwd VARCHAR(32) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE admins(
    id INT AUTO_INCREMENT,
    usuario_id INT,
    PRIMARY KEY(usuario_id),
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE localidades(
    id INT AUTO_INCREMENT,
    admin_id INT,
    cidadeprinc_id INT,
    PRIMARY KEY(id),
    FOREIGN KEY(admin_id) REFERENCES admins(id),
    FOREIGN KEY(cidadeprinc_id) REFERENCES cidades(id)
);

ALTER TABLE cidades
ADD CONSTRAINT fk_cidloc FOREIGN KEY(localidade_id) REFERENCES localidades(id);

CREATE TABLE categorias(
    id INT AUTO_INCREMENT,
    admin_id INT,
    catpai_id INT DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(admin_id) REFERENCES admins(id),
    FOREIGN KEY(catpai_id) REFERENCES categorias(id)
);
CREATE TABLE tiposServico(
    id INT AUTO_INCREMENT,
    admin_id INT,
    categoria_id INT,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    PRIMARY KEY(id),
    FOREIGN KEY(admin_id) REFERENCES admins(id),
    FOREIGN KEY(categoria_id) REFERENCES categorias(id)
);

CREATE TABLE anunciantes(
    id INT AUTO_INCREMENT,
    usuario_id INT,
    nome VARCHAR(100),
    empresa VARCHAR(100),
    cpf VARCHAR(11) UNIQUE, -- SEM MÁSCARA
    PRIMARY KEY(usuario_id),
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE anuncios(
    id INT AUTO_INCREMENT,
    anunciante_id INT,
    localidade_id INT,
    tpservico_id INT,
    descricao VARCHAR(200),
    desc_longa TEXT,
    valor DECIMAL(15,2),
    tempo TIME,
    PRIMARY KEY(id),
    FOREIGN KEY(anunciante_id) REFERENCES anunciantes(id),
    FOREIGN KEY(localidade_id) REFERENCES localidades(id),
    FOREIGN KEY(tpservico_id) REFERENCES tiposServico(id)
);

CREATE TABLE disponibilidades(
    id INT AUTO_INCREMENT,
    anuncio_id INT,
    dia_semana ENUM('Domingo', 'Segunda-Feira', 'Terça-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sábado'),
    hr_ini TIME,
    hr_fim TIME,
    PRIMARY KEY(id),
    FOREIGN KEY(anuncio_id) REFERENCES anuncios(id)
);

CREATE TABLE imagens(
    id INT AUTO_INCREMENT,
    anuncio_id INT,
    link VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY(anuncio_id) REFERENCES anuncios(id)
);
