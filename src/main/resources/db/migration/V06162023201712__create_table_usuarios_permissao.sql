CREATE TABLE IF NOT EXISTS usuario_permissoes (
    usu_perm_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usu_perm_usuario_id BIGINT,
    usu_perm_perm_id BIGINT,
    FOREIGN KEY (usu_perm_usuario_id) REFERENCES usuario(usu_id),
    FOREIGN KEY (usu_perm_perm_id) REFERENCES permissao(per_id)
)DEFAULT CHARSET=utf8mb4;