package com.wk.testejava.repositories;

import com.wk.testejava.models.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findBySexoIgnoreCaseAndImcGreaterThan(String sexo, double imc);
    Page<Pessoa> findByCpfContainsIgnoreCase(String cpf, Pageable pageable);
    boolean existsByCpf(String cpf);

//    @Query(value = "SELECT AVG(YEAR(CAST(CURRENT_DATE() AS DATE)) - YEAR(CAST(STR_TO_DATE(p.data_nasc, '%d-%m-%Y') AS DATE))) " +
//            "FROM Pessoa p WHERE p.tipo_sanguineo = :tipoSanguineo")
//    Double calcularMediaIdadePorTipoSanguineo(@Param("tipoSanguineo") String tipoSanguineo);

    long countBySexo(String sexo);

    long countBySexoAndImcGreaterThan(String sexo, double imc);

    @Query("SELECT DISTINCT p.tipo_sanguineo FROM Pessoa p")
    List<String> findDistinctTipoSanguineo();

    @Query("SELECT p FROM Pessoa p WHERE p.tipo_sanguineo = :tipoSanguineo")
    List<Pessoa> findByTipoSanguineo(@Param("tipoSanguineo") String tipoSanguineo);

}