package com.leilao.backend.model;

import com.leilao.backend.enums.TipoPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{validation.tipo.notblank}")
    @Column(name = "tipo_perfil")
    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;

    public Perfil atualizaValores(Perfil perfilAtualizado) {
        this.setTipo(perfilAtualizado.getTipo());
        return this;
    }
}
