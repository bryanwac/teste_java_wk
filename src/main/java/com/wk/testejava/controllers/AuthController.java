package com.wk.testejava.controllers;

import com.wk.testejava.payload.LoginRequest;
import com.wk.testejava.payload.RegistroRequest;
import com.wk.testejava.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/m")
public class AuthController {

    private final UsuarioService service;


    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(service.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/permissoes")
    public ResponseEntity<?> buscaPermissoes() {
        return ResponseEntity.ok(service.buscaPermissoes());
    }
}
