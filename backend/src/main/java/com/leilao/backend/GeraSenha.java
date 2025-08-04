package com.leilao.backend;

import com.leilao.backend.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeraSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = encoder.encode("pamonha");
        System.out.println(senha);

    }
}
