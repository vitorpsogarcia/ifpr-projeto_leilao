package com.leilao.backend.service;

import com.leilao.backend.model.Lance;
import com.leilao.backend.repository.LanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanceService {

    @Autowired
    private LanceRepository repository;

    public Lance criar(Lance lance) {
        return repository.save(lance);
    }

    public Lance buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Lance> listarTodos() {
        return repository.findAll();
    }

    public Lance atualizar(Long id, Lance lance) {
        if (repository.existsById(id)) {
            lance.setId(id);
            return repository.save(lance);
        }
        return null;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

