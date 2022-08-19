CREATE TABLE `despesas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` longtext NOT NULL,
  `valor` double NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;