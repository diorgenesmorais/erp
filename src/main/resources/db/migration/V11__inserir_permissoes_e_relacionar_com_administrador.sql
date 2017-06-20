INSERT INTO permissao (id, name) VALUES (1, 'CADASTRAR_USUARIO'), (2, 'CADASTRAR_CIDADE');

INSERT INTO grupo_permissao (grupo_id, permissao_id) VALUES (1, 1), (1, 2);

INSERT INTO usuario_grupo (usuario_id, grupo_id) VALUES ((select id from usuario where email='diorgenesmorais@gmail.com'), 1);