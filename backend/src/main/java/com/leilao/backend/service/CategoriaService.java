package com.leilao.backend.service;

import com.leilao.backend.model.Categoria;
import com.leilao.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria criar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Categoria> listarTodos() {
        return repository.findAll();
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        if (repository.existsById(id)) {
            categoria.setId(id);
            return repository.save(categoria);
        }
        return null;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

