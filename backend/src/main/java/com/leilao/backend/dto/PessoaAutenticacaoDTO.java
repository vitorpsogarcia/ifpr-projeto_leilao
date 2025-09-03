package com.leilao.backend.dto;

import lombok.Data;

@Data
public class PessoaAutenticacaoDTO {
    private Long id;
    private String email;
    private String nome;
    private String token;
}
