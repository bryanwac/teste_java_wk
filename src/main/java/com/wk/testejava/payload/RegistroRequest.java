package com.wk.testejava.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O sobrenome é obrigatório.")
    private String sobrenome;
    @NotBlank(message = "O email é obrigatório.")
    private String email;
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;
    @NotBlank(message = "O Telefone é obrigatório.")
    private String telefone;

    private Set<String> role;

}