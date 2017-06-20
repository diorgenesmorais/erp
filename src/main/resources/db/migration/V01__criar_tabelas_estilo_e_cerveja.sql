CREATE TABLE `estilo` (
	`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	`nome` varchar(15) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cerveja` (
	`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	`sku` varchar(10) NOT NULL,
	`nome` varchar(30) NOT NULL,
	`descricao` TEXT NOT NULL,
	`valor` DECIMAL(10,2) NOT NULL,
	`teor_alcoolico` DECIMAL(10,2) NOT NULL,
	`comissao` DECIMAL(10,2) NOT NULL,
	`qtde` INTEGER NOT NULL,
	`origem` TINYINT NOT NULL,
	`sabor` TINYINT NOT NULL,
	`estilo_id` bigint UNSIGNED NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`estilo_id`) REFERENCES `estilo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estilo VALUES (0, 'Amber Lager');
INSERT INTO estilo VALUES (0, 'Dark Lager');
INSERT INTO estilo VALUES (0, 'Pale Lager');
INSERT INTO estilo VALUES (0, 'Pilsner');