package com.leilao.backend.dto;

import lombok.Data;

@Data
public class PessoaRequestDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;


}
