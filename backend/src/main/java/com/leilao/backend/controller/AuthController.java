package com.leilao.backend.controller;

import com.leilao.backend.dto.PessoaRequestDTO;
import com.leilao.backend.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AuthController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public String login(@RequestBody PessoaRequestDTO pessoaRequestDTO){
        return autenticacaoService.autenticar(pessoaRequestDTO);
    }
}
