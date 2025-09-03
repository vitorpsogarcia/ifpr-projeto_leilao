package com.leilao.backend.service;

import com.leilao.backend.exception.NaoEncontradoExcecao;
import com.leilao.backend.model.Perfil;
import com.leilao.backend.repository.PerfilRepository;
import com.leilao.backend.utils.GenericUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Date;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public Perfil inserir(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public Perfil alterar(Perfil perfil) {
        Perfil perfilBanco = buscarPorId(perfil.getId());
        Perfil perfilAtualizado = GenericUpdater.atualizaCampos(perfilBanco, perfil);
        perfilAtualizado.setAtualizadoEm(new Date());
        return perfilRepository.save(perfilAtualizado);
    }

    public void excluir(Long id) {
        Perfil perfilBanco = buscarPorId(id);
        perfilRepository.delete(perfilBanco);
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoExcecao(messageSource.getMessage("perfil.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Perfil> buscarTodos(Pageable pageable) {
        return perfilRepository.findAll(pageable);
    }

}
