package com.wk.testejava.repositories;

import com.wk.testejava.models.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findBySexoIgnoreCaseAndImcGreaterThan(String sexo, double imc);
    Page<Pessoa> findByCpfContainsIgnoreCase(String cpf, Pageable pageable);
    boolean existsByCpf(String cpf);

    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(p.data_nasc)) FROM Pessoa p WHERE p.tipo_sanguineo = :tipoSanguineo")
    Double calcularMediaIdadePorTipoSanguineo(@Param("tipoSanguineo") String tipoSanguineo);

    long countBySexo(String sexo);

    long countBySexoAndImcGreaterThan(String sexo, double imc);

    List<String> findDistinctTipoSanguineo();
}