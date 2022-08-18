CREATE TABLE `usuarios_perfis_acesso` (
  `id_usuario` bigint NOT NULL,
  `id_perfil_acesso` bigint NOT NULL,
  KEY `usuarios_perfis_acesso_FK` (`id_usuario`),
  KEY `usuarios_perfis_acesso_FK_1` (`id_perfil_acesso`),
  CONSTRAINT `usuarios_perfis_acesso_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `usuarios_perfis_acesso_FK_1` FOREIGN KEY (`id_perfil_acesso`) REFERENCES `perfis_acesso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3