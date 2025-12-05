package com.leilao.backend.dto;

import com.leilao.backend.enums.TipoPerfil;
import lombok.Data;

import java.util.Set;

@Data
public class PessoaAutenticacaoDTO {
    private Long id;
    private String email;
    private String nome;
    private String token;
    private Set<TipoPerfil> perfis;
}
