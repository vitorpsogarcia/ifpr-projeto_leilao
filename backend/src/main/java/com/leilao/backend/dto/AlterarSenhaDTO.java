package com.leilao.backend.dto;

import lombok.Data;

@Data
public class AlterarSenhaDTO {
    private String email;
    private String codigo;
    private String senha;
}
