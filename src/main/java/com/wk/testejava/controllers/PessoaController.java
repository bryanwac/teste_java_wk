package com.wk.testejava.controllers;

import com.wk.testejava.models.Pessoa;
import com.wk.testejava.services.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<?> criaPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(service.criaPessoa(pessoa));
    }

    @PostMapping("/cria-varias")
    public ResponseEntity<?> criaPessoas(@RequestBody List<Pessoa> listaPessoas) {
        try {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                service.criaPessoas(listaPessoas);
                return null;
            });
            return ResponseEntity.accepted().body("O processamento da lista ocorrerá em segundo plano.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar lista: " + e.getMessage());
        }
    }
}
