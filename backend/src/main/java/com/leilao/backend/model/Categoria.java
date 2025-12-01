package com.leilao.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "categoria")
@EntityListeners(AuditingEntityListener.class)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da categoria deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @Column(name="criado_em")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;

    @Column(name="atualizado_em", nullable = true)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizadoEm;

    @Column(name="excluido_em", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date excluidoEm;
}
