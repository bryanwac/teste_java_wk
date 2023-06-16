package com.wk.testejava.repositories;

import com.wk.testejava.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Boolean existsUsuarioByEmail(String email);

    Boolean existsUsuarioByCpf(String cpf);

    Optional<Usuario> findByCpf(String cpf);
}
