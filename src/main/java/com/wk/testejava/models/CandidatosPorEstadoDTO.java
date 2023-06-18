package com.wk.testejava.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CandidatosPorEstadoDTO {

    private String pes_estado;
    private Long quantidade;

    public CandidatosPorEstadoDTO(String estado, Long quantidade) {
        this.pes_estado = estado;
        this.quantidade = quantidade;
    }
}
