CREATE TABLE cliente (
	id bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	nome varchar(64) NOT NULL,
	tipo_pessoa varchar(8) NOT NULL,
	cpf_cnpj varchar(14) NOT NULL,
	telefone varchar(11),
	email varchar(64),
	logradouro varchar(64),
	numero smallint(5),
	complemento varchar(64),
	cep varchar(8),
	cidade_id bigint(20) UNSIGNED,
	PRIMARY KEY (id),
	KEY FK_cliente_cidade_id (cidade_id),
	CONSTRAINT FK_clinte_cidade_id FOREIGN KEY (cidade_id) REFERENCES cidade (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;