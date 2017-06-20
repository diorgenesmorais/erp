CREATE TABLE `cidade` (
	`id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	`nome` varchar(20) NOT NULL,
	`estado` varchar(2) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;