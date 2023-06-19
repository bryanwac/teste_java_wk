package com.wk.testejava.repositories;

import com.wk.testejava.models.CandidatosPorEstadoDTO;
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

    long countBySexo(String sexo);

    long countBySexoAndImcGreaterThan(String sexo, double imc);

    @Query("SELECT DISTINCT p.tipo_sanguineo FROM Pessoa p")
    List<String> findDistinctTipoSanguineo();

    @Query("SELECT p FROM Pessoa p WHERE p.tipo_sanguineo = :tipoSanguineo")
    List<Pessoa> findByTipoSanguineo(@Param("tipoSanguineo") String tipoSanguineo);

    @Query("SELECT COUNT(p) FROM Pessoa p WHERE p.tipo_sanguineo IN (?1) AND p.podeDoarSangue = true")
    long countByTipoSanguineoAndPodeDoarSangueTrueIn(List<String> tiposSanguineos);

    @Query(value = "SELECT estado, COUNT(*) AS quantidade FROM Pessoa GROUP BY estado")
    List<Object[]> countPessoasPorEstado();

}