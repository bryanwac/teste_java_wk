package com.wk.testejava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IMCMedioPorFaixaIdadeDTO {

    private String faixaIdade;
    private double imcMedio;

    public IMCMedioPorFaixaIdadeDTO(String faixaIdade, double imcMedio) {
        this.faixaIdade = faixaIdade;
        this.imcMedio = imcMedio;
    }
}
