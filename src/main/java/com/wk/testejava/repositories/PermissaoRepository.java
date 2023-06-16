package com.wk.testejava.repositories;

import com.wk.testejava.enums.EPermissao;
import com.wk.testejava.models.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    Optional<Permissao> findByNome(EPermissao nome);
}
