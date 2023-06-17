package com.wk.testejava.services;

import com.wk.testejava.exception.ApiException;
import com.wk.testejava.models.Pessoa;
import com.wk.testejava.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;

    public Pessoa criaPessoa(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Async("threadPoolTaskExecutor")
    public void criaPessoas(List<Pessoa> listaPessoas) {
        try {
            List<Pessoa> pessoasASalvar = new ArrayList<>();
            for (Pessoa pessoa : listaPessoas) {
                if (!repository.existsByCpf(pessoa.getCpf())) {
                    pessoasASalvar.add(pessoa);
                }
            }
            if (!pessoasASalvar.isEmpty()) {
                repository.saveAll(pessoasASalvar);
            } else throw new ApiException("A lista de pessoas não pode ser uma lista vazia");
        } catch (ApiException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao processar lista!");
        }
    }

    public Pessoa patch(Long id, Map<Object, Object> map) {
        try {
            Pessoa registro = repository.findById(id).orElseThrow(() -> {
                throw new ApiException("Pessoa não encontrada");
            });
            map.forEach((key, value) -> {
                Optional<Field> optionalField = Optional.ofNullable(ReflectionUtils.findField(Pessoa.class, key.toString()));
                if (optionalField.isPresent()) {
                    Field field = optionalField.get();
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, registro, value);
                }
            });
            return repository.save(registro);
        } catch (ApiException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Erro ao atualizar informações da pessoa");
        }
    }

}
