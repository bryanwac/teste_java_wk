package com.wk.testejava.services;

import com.wk.testejava.dto.EstatisticaDTO;
import com.wk.testejava.dto.EstatisticaTipoSangDTO;
import com.wk.testejava.exception.ApiException;
import com.wk.testejava.models.Pessoa;
import com.wk.testejava.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private final PessoaRepository pessoaRepository;

    public EstatisticaDTO calcularEstatisticas() {
        try {
            EstatisticaDTO estatisticaDTO = new EstatisticaDTO();

            // Total de pessoas
            long totalPessoas = pessoaRepository.count();
            estatisticaDTO.setTotalPessoas(Math.toIntExact(totalPessoas));

            // Total de homens
            long totalHomens = pessoaRepository.countBySexo("Masculino");
            estatisticaDTO.setTotalHomens(Math.toIntExact(totalHomens));

            // Total de mulheres
            long totalMulheres = pessoaRepository.countBySexo("Feminino");
            estatisticaDTO.setTotalMulheres(Math.toIntExact(totalMulheres));

            // Total de homens obesos
            long obesosHomens = pessoaRepository.countBySexoAndImcGreaterThan("Masculino", 30.0);
            estatisticaDTO.setObesosHomens(Math.toIntExact(obesosHomens));

            // Total de mulheres obesas
            long obesosMulheres = pessoaRepository.countBySexoAndImcGreaterThan("Feminino", 30.0);
            estatisticaDTO.setObesosMulheres(Math.toIntExact(obesosMulheres));

            // Estatísticas por tipo sanguíneo
            List<EstatisticaTipoSangDTO> estatisticasTipoSang = new ArrayList<>();
            List<String> tiposSanguineos = pessoaRepository.findDistinctTipoSanguineo();

            for (String tipoSanguineo : tiposSanguineos) {
                List<Pessoa> pessoasPorTipoSanguineo = pessoaRepository.findByTipoSanguineo(tipoSanguineo);
                if (!pessoasPorTipoSanguineo.isEmpty()) {
                    Double mediaIdade = calcularMediaIdade(pessoasPorTipoSanguineo);
                    EstatisticaTipoSangDTO estatisticaTipoSangDTO = new EstatisticaTipoSangDTO();
                    estatisticaTipoSangDTO.setTipoSanguineo(tipoSanguineo);
                    estatisticaTipoSangDTO.setMediaIdade(mediaIdade);
                    estatisticasTipoSang.add(estatisticaTipoSangDTO);
                }
            }

            estatisticaDTO.setEstatisticaPorTipoSang(estatisticasTipoSang);

            return estatisticaDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao obter estatísticas");
        }
    }

    private Double calcularMediaIdade(List<Pessoa> pessoas) {
        LocalDate currentDate = LocalDate.now();
        int totalIdades = 0;

        for (Pessoa pessoa : pessoas) {
            LocalDate dataNasc = LocalDate.parse(pessoa.getData_nasc(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int idade = Period.between(dataNasc, currentDate).getYears();
            totalIdades += idade;
        }

        return totalIdades / (double) pessoas.size();
    }

}
