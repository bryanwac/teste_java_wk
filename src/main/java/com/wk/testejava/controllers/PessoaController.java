package com.wk.testejava.controllers;

import com.wk.testejava.models.Pessoa;
import com.wk.testejava.services.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> atualizarPessoa(@PathVariable Long id, @RequestBody Map<Object, Object> map) {
        return ResponseEntity.status(HttpStatus.OK).body(service.patch(id, map));
    }

    @GetMapping
    public ResponseEntity<?> buscaTodos(@RequestParam Optional<Integer> page,
                                        @RequestParam Optional<Integer> size,
                                        @RequestParam Optional<String> cpf) {
        try {
            return cpf.map(s -> ResponseEntity.ok().body(service.buscaPorCpfContem(s, page.orElse(0), size.orElse(10)))).orElseGet(() -> ResponseEntity.ok().body(service.buscaTodos(page.orElse(0), size.orElse(10))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
