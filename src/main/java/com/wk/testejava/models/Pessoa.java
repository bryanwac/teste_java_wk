package com.wk.testejava.models;

import com.wk.testejava.exception.ApiException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
    @Column(name = "pes_cpf", unique = true)
    private String cpf;
    @Column(name = "pes_rg")
    private String rg;
    @Column(name = "pes_data_nascimento")
    private String data_nasc;
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
    private String telefone_fixo;
    @Column(name = "pes_celular")
    private String celular;
    @Column(name = "pes_altura")
    private double altura;
    @Column(name = "pes_peso")
    private double peso;
    @Column(name = "pes_tipo_sanguineo")
    private String tipo_sanguineo;

    @Column(name = "pes_imc")
    private double imc;

    @Column(name = "pes_pode_doar_sangue")
    private boolean podeDoarSangue = false;

    @PrePersist
    private void verificaPodeDoarEIMC() {

        //Calculo IMC
        double alturaMetros = this.altura;
        this.imc = this.peso / (alturaMetros * alturaMetros);

        //Verificação para poder doar
        LocalDate hoje = LocalDate.now();
        String dataNascimentoSemEscape = this.data_nasc.replace("\\", "");
        LocalDate dataNascimentoLocalDate = LocalDate.parse(dataNascimentoSemEscape, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int idade = Period.between(dataNascimentoLocalDate, hoje).getYears();
        if (idade >= 16 && idade <= 69 && this.peso > 50) {
            this.podeDoarSangue = true;
        } else {
            this.podeDoarSangue = false;
        }
    }

}
