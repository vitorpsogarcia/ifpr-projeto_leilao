package com.leilao.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comentario;

    private Integer nota;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "escritor_id")
    private Pessoa escritor;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Pessoa destinatario;

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
