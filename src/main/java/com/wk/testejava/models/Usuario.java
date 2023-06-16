package com.wk.testejava.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "usu_email"),
                @UniqueConstraint(columnNames = "usu_cpf")
        })
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @Column(name = "usu_nome", nullable = false)
    private String nome;

    @Column(name = "usu_sobrenome", nullable = false)
    private String sobrenome;
    @Column(name = "usu_email", nullable = false)
    private String email;
    @ReadOnlyProperty
    @Column(name = "usu_senha", nullable = false)
    private String senha;
    @Column(name = "usu_cpf", nullable = false)
    private String cpf;

    @Column(name = "usu_telefone", nullable = false)
    private String telefone;

    @ReadOnlyProperty
    @Column(name = "usu_senha_antiga")
    private String senhaAntiga = null;

    @ReadOnlyProperty
    @Column(name = "usu_senha_foi_alterada")
    private Boolean senhaFoiAlterada = false;

    @ReadOnlyProperty
    @Column(name = "usu_data_alteracao_senha")
    private LocalDateTime dataAlteracaoSenha = null;

    @ReadOnlyProperty
    @Column(name = "usu_data_criacao_conta")
    private LocalDateTime dataCriacaoConta;

    @PrePersist
    void beforeInsert() {
        if (cpf != null)
            this.cpf = cpf.trim().replace(".", "").replace("-", "");

        this.dataCriacaoConta = LocalDateTime.now();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissoes",
            joinColumns = @JoinColumn(name = "usu_perm_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "usu_perm_perm_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public void setPermissoes(Set<Permissao> roles) {
        this.permissoes = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (var r : this.permissoes) {
            var sga = new SimpleGrantedAuthority(r.getNome().toString());
            authorities.add(sga);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
