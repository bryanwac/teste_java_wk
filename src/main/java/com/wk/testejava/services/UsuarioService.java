package com.wk.testejava.services;

import com.wk.testejava.enums.EPermissao;
import com.wk.testejava.exception.ApiException;
import com.wk.testejava.models.Permissao;
import com.wk.testejava.models.Usuario;
import com.wk.testejava.payload.AuthenticationResponse;
import com.wk.testejava.payload.LoginRequest;
import com.wk.testejava.payload.RegistroRequest;
import com.wk.testejava.repositories.PermissaoRepository;
import com.wk.testejava.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PermissaoRepository permRepository;

    public AuthenticationResponse registrar(RegistroRequest registroRequest) {
        try {
            if (repository.existsUsuarioByEmail(registroRequest.getEmail()))
                throw new ApiException("O Email informado já está em uso.");

            if (repository.existsUsuarioByCpf(registroRequest.getCpf())) throw new ApiException("CPF inválido.");

            var usuario = Usuario.builder()
                    .nome(registroRequest.getNome())
                    .sobrenome(registroRequest.getSobrenome())
                    .email(registroRequest.getEmail())
                    .senha(passwordEncoder.encode(registroRequest.getSenha()))
                    .cpf(registroRequest.getCpf())
                    .telefone(registroRequest.getTelefone())
                    .build();

            Set<String> strRoles = registroRequest.getRole();
            Set<Permissao> permissoesUsuario = new HashSet<>();

            String token = null;

            if (strRoles == null) {
                Permissao userRole = permRepository.findByNome(EPermissao.PERM_USER)
                        .orElseThrow(() -> new ApiException("Permissão não encontrada"));
                permissoesUsuario.add(userRole);

                usuario.setPermissoes(permissoesUsuario);
                try {
                    repository.save(usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ApiException("Erro ao salvar usuário");
                }

                token = tokenService.gerarToken(usuario);

            }
            return AuthenticationResponse.builder().token(token).build();
        } catch (ApiException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao registrar usuário.");
        }
    }

    public AuthenticationResponse login(LoginRequest request) {
        try {
            if (!repository.existsUsuarioByEmail(request.getEmail()))
                throw new ApiException("Email ou senha inválidos");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getSenha()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Usuario usuario = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            String token = tokenService.gerarToken(usuario);
            return AuthenticationResponse.builder().token(token).build();

        } catch (ApiException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao fazer login");
        }
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> {
            throw new ApiException("Usuário não encontrado com o email informado");
        });
    }
}
