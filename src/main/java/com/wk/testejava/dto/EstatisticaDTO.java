package com.wk.testejava.dto;

import com.wk.testejava.models.CandidatosPorEstadoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstatisticaDTO {

    private int totalPessoas;
    private int totalHomens;
    private int totalMulheres;
    private int obesosHomens;
    private int obesosMulheres;
    private List<EstatisticaTipoSangDTO> estatisticaPorTipoSang;

    private List<EstatisticaRelacaoDoadoresDTO> relacaoDoadores;

    private List<CandidatosPorEstadoDTO> candidatosPorEstado;

    private List<IMCMedioPorFaixaIdadeDTO> imcMedioPorFaixaEtaria;


}
