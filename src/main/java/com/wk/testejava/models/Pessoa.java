package com.wk.testejava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @Column(name = "pes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pes_nome")
    private String nome;
    @Column(name = "pes_cpf")
    private String cpf;
    @Column(name = "pes_rg")
    private String rg;
    @Column(name = "pes_data_nascimento")
    private String dataNascimento;
    @Column(name = "pes_sexo")
    private String sexo;
    @Column(name = "pes_mae")
    private String mae;
    @Column(name = "pes_pai")
    private String pai;
    @Column(name = "pes_email")
    private String email;
    @Column(name = "pes_cep")
    private String cep;
    @Column(name = "pes_endereco")
    private String endereco;
    @Column(name = "pes_numero")
    private int numero;
    @Column(name = "pes_bairro")
    private String bairro;
    @Column(name = "pes_cidade")
    private String cidade;
    @Column(name = "pes_estado")
    private String estado;
    @Column(name = "pes_telefone_fixo")
    private String telefoneFixo;
    @Column(name = "pes_celular")
    private String celular;
    @Column(name = "pes_altura")
    private double altura;
    @Column(name = "pes_peso")
    private double peso;
    @Column(name = "pes_tipo_sanguineo")
    private String tipoSanguineo;

    @Column(name = "pes_imc")
    private double imc;

    @PrePersist
    private void calcularIMC() {
        double alturaMetros = altura / 100;
        this.imc = peso / (alturaMetros * alturaMetros);
    }

}
