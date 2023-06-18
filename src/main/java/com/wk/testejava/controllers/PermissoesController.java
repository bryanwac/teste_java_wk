package com.wk.testejava.controllers;

import com.wk.testejava.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissoes")
public class PermissoesController {

    private final UsuarioService service;

    @GetMapping()
    public ResponseEntity<?> buscaPermissoes() {
        return ResponseEntity.ok(service.buscaPermissoes());
    }
}
