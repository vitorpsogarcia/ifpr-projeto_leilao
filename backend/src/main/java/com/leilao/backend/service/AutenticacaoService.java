package com.leilao.backend.service;

import com.leilao.backend.dto.PessoaAutenticacaoDTO;
import com.leilao.backend.dto.PessoaRequestDTO;
import com.leilao.backend.exception.UsuarioJaExisteExcecao;
import com.leilao.backend.model.Pessoa;
import com.leilao.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leilao.backend.security.TokenService;

import java.util.Objects;
import java.util.Optional;

@Service
public class AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Autowired
    private TokenService tokenService;
    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaAutenticacaoDTO autenticar(PessoaRequestDTO pessoa) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(pessoa.getEmail(), pessoa.getSenha())
        );

        Optional<Pessoa> pessoaBanco = pessoaRepository.findByEmail(pessoa.getEmail());

        PessoaAutenticacaoDTO dto = new PessoaAutenticacaoDTO();
        if  (pessoaBanco.isPresent()) {
            dto.setEmail(pessoaBanco.get().getEmail());
            dto.setNome(pessoaBanco.get().getNome());
            dto.setId(pessoaBanco.get().getId());
            dto.setToken(tokenService.gerarToken(pessoaBanco.get()));
        }

        return dto;
    }

    public Boolean registrar(PessoaRequestDTO pessoa){

        Optional<Pessoa> pessoa1 = this.pessoaRepository.findByEmail(pessoa.getEmail());
        if (pessoa1.isEmpty()) pessoa1 = this.pessoaRepository.findByCpf(pessoa.getCpf());

        if (pessoa1.isPresent()) {
            throw new UsuarioJaExisteExcecao("Já existe um usuário com o email ou cpf informado");
        }

        String senhaCriptografada = encoder.encode(pessoa.getSenha());

        Pessoa pessoaSalvar = new Pessoa();
        pessoaSalvar.setEmail(pessoa.getEmail());
        pessoaSalvar.setNome(pessoa.getNome());
        pessoaSalvar.setSenha(senhaCriptografada);
        pessoaSalvar.setCpf(pessoa.getCpf());

        this.pessoaRepository.save(pessoaSalvar);
        return true;
    }
}
