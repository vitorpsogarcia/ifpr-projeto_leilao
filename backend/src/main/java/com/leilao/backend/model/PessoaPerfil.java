package com.leilao.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "pessoa_perfil")
public class PessoaPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_perfil")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name="id_pessoa")
    @JsonIgnore
    private Pessoa pessoa;
}
