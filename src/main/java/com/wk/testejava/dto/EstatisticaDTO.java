package com.wk.testejava.dto;

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


}
