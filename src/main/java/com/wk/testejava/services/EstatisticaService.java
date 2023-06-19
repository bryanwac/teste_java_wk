package com.wk.testejava.services;

import com.wk.testejava.dto.EstatisticaDTO;
import com.wk.testejava.dto.EstatisticaRelacaoDoadoresDTO;
import com.wk.testejava.dto.EstatisticaTipoSangDTO;
import com.wk.testejava.dto.IMCMedioPorFaixaIdadeDTO;
import com.wk.testejava.exception.ApiException;
import com.wk.testejava.models.CandidatosPorEstadoDTO;
import com.wk.testejava.models.Pessoa;
import com.wk.testejava.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

            // Estatísticas por tipo sanguíneo
            List<EstatisticaRelacaoDoadoresDTO> relacaoDoadores = new ArrayList<>();

            for (String tipoSanguineo : tiposSanguineos) {
                EstatisticaRelacaoDoadoresDTO relacaoDoadoresDTO = new EstatisticaRelacaoDoadoresDTO();
                relacaoDoadoresDTO.setTipo(tipoSanguineo);
                relacaoDoadoresDTO.setPossiveisDoadores(calcularPossiveisDoadores(tipoSanguineo));
                relacaoDoadores.add(relacaoDoadoresDTO);
            }

            estatisticaDTO.setRelacaoDoadores(relacaoDoadores);

            estatisticaDTO.setCandidatosPorEstado(this.obterCandidatosPorEstado());

            estatisticaDTO.setImcMedioPorFaixaEtaria(this.obterIMCMedioPorFaixaIdade());

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

    private long calcularPossiveisDoadores(String tipoReceptor) {
        long quantidade = 0;

        switch (tipoReceptor) {
            case "A+":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("A+", "A-", "O+", "O-")
                );
                break;
            case "A-":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("A-", "O-")
                );
                break;
            case "B+":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("B+", "B-", "O+", "O-")
                );
                break;
            case "B-":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("B-", "O-")
                );
                break;
            case "AB+":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
                );
                break;
            case "O+":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Arrays.asList("O+", "O-")
                );
                break;
            case "O-":
                quantidade = pessoaRepository.countByTipoSanguineoAndPodeDoarSangueTrueIn(
                        Collections.singletonList("O-")
                );
                break;
        }

        return quantidade;
    }

    public List<CandidatosPorEstadoDTO> obterCandidatosPorEstado() {
        List<Object[]> resultados = pessoaRepository.countPessoasPorEstado();
        List<CandidatosPorEstadoDTO> candidatosPorEstado = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String estado = (String) resultado[0];
            Long quantidade = ((Number) resultado[1]).longValue();
            candidatosPorEstado.add(new CandidatosPorEstadoDTO(estado, quantidade));
        }

        return candidatosPorEstado;
    }

    public List<IMCMedioPorFaixaIdadeDTO> obterIMCMedioPorFaixaIdade() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<IMCMedioPorFaixaIdadeDTO> imcMedioPorFaixaIdade = pessoas.stream()
                .collect(Collectors.groupingBy(pessoa -> {
                    LocalDate dataNascimento = LocalDate.parse(pessoa.getData_nasc(), formatter);
                    int faixa = (LocalDate.now().getYear() - dataNascimento.getYear()) / 10;
                    int faixaInicial = faixa * 10;
                    int faixaFinal = faixaInicial + 9;
                    return faixaInicial + " a " + faixaFinal;
                }))
                .entrySet().stream()
                .map(entry -> {
                    String faixaIdade = entry.getKey();
                    double imcMedio = entry.getValue().stream().mapToDouble(Pessoa::getImc).average().orElse(0.0);
                    return new IMCMedioPorFaixaIdadeDTO(faixaIdade, imcMedio);
                })
                .collect(Collectors.toList());

        return imcMedioPorFaixaIdade;
    }

}
