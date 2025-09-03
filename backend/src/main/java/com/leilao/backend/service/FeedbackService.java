package com.leilao.backend.service;

import com.leilao.backend.dto.FeedbackDTO;
import com.leilao.backend.exception.NaoEncontradoExcecao;
import com.leilao.backend.model.Feedback;
import com.leilao.backend.model.Feedback;
import com.leilao.backend.model.Pessoa;
import com.leilao.backend.repository.FeedbackRepository;
import com.leilao.backend.repository.PessoaRepository;
import com.leilao.backend.utils.GenericUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback inserir(FeedbackDTO feedbackDTO) {
        Optional<Pessoa> escritor = pessoaRepository.findById(feedbackDTO.getIdEscritor());
        Optional<Pessoa> destinatario = pessoaRepository.findById(feedbackDTO.getIdDestinatario());
        if (escritor.isEmpty()) {
            throw new NaoEncontradoExcecao("Escritor não encontrado");
        }
        if (destinatario.isEmpty()) {
            throw new NaoEncontradoExcecao("Escritor não encontrado");
        }
        Feedback feedback = new Feedback();
        feedback.setComentario(feedbackDTO.getComentario());
        feedback.setEscritor(escritor.get());
        feedback.setDestinatario(destinatario.get());
        feedback.setNota(feedbackDTO.getNota());
        feedback.setCriadoEm(feedbackDTO.getDataHora());
        return feedbackRepository.save(feedback);
    }

    public Feedback alterar(Feedback feedback) {
        Feedback feedbackBanco = buscarPorId(feedback.getId());
        Feedback feedbackAtualizado = GenericUpdater.atualizaCampos(feedbackBanco, feedback);
        feedbackAtualizado.setAtualizadoEm(new Date());
        return feedbackRepository.save(feedbackAtualizado);
    }

    public void excluir(Long id) {
        Feedback feedbackBanco = buscarPorId(id);
        feedbackRepository.delete(feedbackBanco);
    }

    public Feedback buscarPorId(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoExcecao(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Feedback> buscarTodos(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

}
