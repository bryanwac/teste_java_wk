package com.wk.testejava.controllers;

import com.wk.testejava.services.EstatisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final EstatisticaService service;

    @GetMapping
    public ResponseEntity<?> buscaEstatisticas() {
        return ResponseEntity.ok(service.calcularEstatisticas());
    }
}
