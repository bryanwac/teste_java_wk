CREATE TABLE IF NOT EXISTS usuario (
    usu_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usu_nome VARCHAR(255) NOT NULL,
    usu_sobrenome VARCHAR(255) NOT NULL,
    usu_email VARCHAR(255) NOT NULL,
    usu_senha VARCHAR(255) NOT NULL,
    usu_cpf VARCHAR(255) NOT NULL,
    usu_telefone VARCHAR(255) NOT NULL,
    usu_senha_antiga VARCHAR(255),
    usu_senha_foi_alterada TINYINT DEFAULT FALSE,
    usu_data_alteracao_senha DATETIME,
    usu_data_criacao_conta DATETIME,
    UNIQUE KEY (usu_email),
    UNIQUE KEY (usu_cpf)
)DEFAULT CHARSET=utf8mb4;
