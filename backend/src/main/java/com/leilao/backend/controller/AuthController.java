package com.leilao.backend.controller;

import com.leilao.backend.dto.AlterarSenhaDTO;
import com.leilao.backend.dto.PessoaAutenticacaoDTO;
import com.leilao.backend.dto.PessoaRequestDTO;
import com.leilao.backend.dto.RecuperarSenhaDTO;
import com.leilao.backend.service.AutenticacaoService;
import com.leilao.backend.service.PessoaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AuthController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private PessoaService pessoaService;

    @Value("${jwt.expiration}")
    private Long expiration;

    @PostMapping("/login")
    public ResponseEntity<PessoaAutenticacaoDTO> login(@RequestBody PessoaRequestDTO pessoaRequestDTO, HttpServletResponse response){
        PessoaAutenticacaoDTO autenticacaoDTO = autenticacaoService.autenticar(pessoaRequestDTO);
        Cookie authCookie = new Cookie("token", autenticacaoDTO.getToken());
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false);
        authCookie.setPath("/");
        authCookie.setMaxAge(expiration.intValue());
        authCookie.setDomain("localhost");
        response.addCookie(authCookie);
        return ResponseEntity.ok(autenticacaoDTO);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie authCookie = new Cookie("token", "");
        authCookie.setMaxAge(0);
        authCookie.setPath("/");
        response.addCookie(authCookie);
        return "Deslogado com sucesso";
    }

    @PostMapping("/registrar")
    public ResponseEntity<Boolean> registrar(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        this.autenticacaoService.registrar(pessoaRequestDTO);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody RecuperarSenhaDTO recuperarSenhaDTO) {
        return ResponseEntity.ok(pessoaService.solicitarRecuperacaoSenha(recuperarSenhaDTO));
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(@RequestBody AlterarSenhaDTO alterarSenhaDTO) throws Exception {
        return ResponseEntity.ok(pessoaService.alterarSenha(alterarSenhaDTO));
    }
}
