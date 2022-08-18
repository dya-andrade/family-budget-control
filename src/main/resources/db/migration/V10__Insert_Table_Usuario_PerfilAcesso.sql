INSERT INTO usuarios
(id, nome, email, senha)
VALUES(1, 'Dyane Andrade','dyane.aaraujo@gmail.com', '39b45170b1a3b8ea045523a9b71975de9e367db5a44416a6c251f6418b0c6939ee5b19fb208a8205'),
(2, 'Dyane Andrade', 'dyane_araujo@outlook.com', '39b45170b1a3b8ea045523a9b71975de9e367db5a44416a6c251f6418b0c6939ee5b19fb208a8205');

INSERT INTO usuarios_perfis_acesso
(id_usuario, id_perfil_acesso)
VALUES(1, 2), (1, 1), (2, 1);

