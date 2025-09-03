package com.leilao.backend.exception;

public class UsuarioJaExisteExcecao extends RuntimeException {

    public UsuarioJaExisteExcecao(String mensagem) {
        super(mensagem);
    }

}
