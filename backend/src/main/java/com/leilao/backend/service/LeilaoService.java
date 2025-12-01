package com.leilao.backend.service;

import com.leilao.backend.model.Leilao;
import com.leilao.backend.repository.LeilaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository repository;

    public Leilao criar(Leilao leilao) {
        return repository.save(leilao);
    }

    public Leilao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Leilao> listarTodos() {
        return repository.findAll();
    }

    public Leilao atualizar(Long id, Leilao leilao) {
        if (repository.existsById(id)) {
            leilao.setId(id);
            return repository.save(leilao);
        }
        return null;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

