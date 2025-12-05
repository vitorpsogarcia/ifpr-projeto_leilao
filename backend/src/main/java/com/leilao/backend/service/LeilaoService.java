package com.leilao.backend.service;

import com.leilao.backend.model.Categoria;
import com.leilao.backend.model.Imagem;
import com.leilao.backend.model.Leilao;
import com.leilao.backend.model.Pessoa;
import com.leilao.backend.repository.CategoriaRepository;
import com.leilao.backend.repository.LeilaoRepository;
import com.leilao.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Leilao criar(Leilao leilao) {
        Pessoa pessoa = pessoaRepository.findById(leilao.getPessoa().getId()).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        leilao.setPessoa(pessoa);

        if (leilao.getCategoria() != null && leilao.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(leilao.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            leilao.setCategoria(categoria);
        }

        if (leilao.getImagens() != null) {
            for (Imagem imagem : leilao.getImagens()) {
                imagem.setLeilao(leilao);
            }
        }

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
