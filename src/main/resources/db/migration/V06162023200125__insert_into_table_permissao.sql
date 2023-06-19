DROP PROCEDURE IF EXISTS `alterTable`;
DELIMITER //

CREATE PROCEDURE `alterTable`()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    SET foreign_key_checks = 0;

    -- Inserir manualmente as permissões na tabela
INSERT INTO permissao(per_nome) VALUES('ROLE_USER');
INSERT INTO permissao(per_nome) VALUES('ROLE_ADMIN');

SET foreign_key_checks = 1;
END //

DELIMITER ;

CALL `alterTable`();

DROP PROCEDURE `alterTable`;
