package com.leilao.backend.service;

import com.leilao.backend.dto.AlterarSenhaDTO;
import com.leilao.backend.dto.RecuperarSenhaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.leilao.backend.exception.NaoEncontradoExcecao;
import com.leilao.backend.exception.NegocioExcecao;
import com.leilao.backend.model.Pessoa;
import com.leilao.backend.repository.PessoaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Random;

@Service
public class PessoaService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public Pessoa inserir(Pessoa pessoa) {
        Pessoa pessoaCadastrada = pessoaRepository.save(pessoa);
         emailService.enviarEmailSimples(pessoaCadastrada.getEmail(), "Cadastrado com Sucesso", "Cadastro no Sistema de Leilão XXX foi feito com sucesso!");
        enviarEmailSucesso(pessoaCadastrada);
        return pessoaCadastrada;
    }

    private void enviarEmailSucesso(Pessoa pessoa) {
        Context context = new Context();
        context.setVariable("nome", pessoa.getNome());
        emailService.emailTemplate(pessoa.getEmail(), "Cadastro Sucesso", context, "cadastroSucesso");
    }

    public Pessoa alterar(Pessoa pessoa) {
        Pessoa pessoaBanco = pessoaRepository.findById(pessoa.getId()).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        if (pessoa.getNome() != null) pessoaBanco.setNome(pessoa.getNome());
        if (pessoa.getEmail() != null) pessoaBanco.setEmail(pessoa.getEmail());
        if (pessoa.getCpf() != null) pessoaBanco.setCpf(pessoa.getCpf());

        if (pessoa.getCategorias() != null) {
            pessoaBanco.getCategorias().clear();
            pessoaBanco.getCategorias().addAll(pessoa.getCategorias());
        }

        if (pessoa.getPerfis() != null) pessoaBanco.setPerfis(pessoa.getPerfis());

        return pessoaRepository.save(pessoaBanco);
    }

    public void excluir(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        pessoaRepository.delete(pessoa);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoExcecao(messageSource.getMessage("pessoa.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Pessoa> buscarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public String solicitarRecuperacaoSenha(RecuperarSenhaDTO recuperarSenhaDTO) {
        Pessoa pessoa = pessoaRepository.findByEmail(recuperarSenhaDTO.getEmail()).orElseThrow(() -> new NaoEncontradoExcecao("Usuário não encontrado"));

        String codigo = String.format("%06d", new Random().nextInt(999999));
        pessoa.setCodigoValidacao(codigo);
        pessoa.setValidadeCodigoValidacao(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // 10 minutos de validade
        pessoaRepository.save(pessoa);

        logger.debug("Código de recuperação para {}: {}", pessoa.getEmail(), codigo);

        Context context = new Context();
        context.setVariable("nome", pessoa.getNome());
        context.setVariable("codigo", codigo);
        emailService.emailTemplate(pessoa.getEmail(), "Recuperação de Senha", context, "recuperarSenha");

        return "Código de recuperação enviado para o seu e-mail";
    }

    public String alterarSenha(AlterarSenhaDTO alterarSenhaDTO) {
        Pessoa pessoa = pessoaRepository.findByEmail(alterarSenhaDTO.getEmail()).orElseThrow(() -> new NaoEncontradoExcecao("Usuário não encontrado"));

        if (pessoa.getCodigoValidacao() == null || !pessoa.getCodigoValidacao().equals(alterarSenhaDTO.getCodigo())) {
            throw new NegocioExcecao("Código de validação inválido");
        }

        if (pessoa.getValidadeCodigoValidacao().before(new Date())) {
            throw new NegocioExcecao("Código de validação expirado");
        }

        pessoa.setSenha(new BCryptPasswordEncoder().encode(alterarSenhaDTO.getSenha()));
        pessoa.setCodigoValidacao(null);
        pessoa.setValidadeCodigoValidacao(null);
        pessoaRepository.save(pessoa);

        return "Senha alterada com sucesso";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pessoaRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado"));
    }
}
