package com.wk.testejava.models;

import com.wk.testejava.enums.EPermissao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "permissao")
public class Permissao {

    @Id
    @Column(name = "per_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "per_nome", length = 100)
    private EPermissao nome;
}