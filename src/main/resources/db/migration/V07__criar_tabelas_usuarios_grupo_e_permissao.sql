CREATE TABLE usuario (
    id BIGINT(20) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    usename VARCHAR(50) NOT NULL,
    email VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    birth_date DATE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo (
    id BIGINT(20) UNSIGNED PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
    id BIGINT(20) UNSIGNED PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_grupo (
    usuario_id BIGINT(20) UNSIGNED NOT NULL,
    grupo_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (usuario_id, grupo_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (grupo_id) REFERENCES grupo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo_permissao (
    grupo_id BIGINT(20) UNSIGNED NOT NULL,
    permissao_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (grupo_id, permissao_id),
    FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;