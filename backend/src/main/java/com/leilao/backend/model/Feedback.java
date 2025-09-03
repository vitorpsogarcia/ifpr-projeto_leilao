package com.leilao.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Length(min = 5)
    private String comentario;

    @Column(nullable = false)
    @Min(0)
    @Max(10)
    private Integer nota;

    @JoinColumn(
      name = "pessoa_escritor",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_feedback_pessoa_escritor")
    )
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa escritor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
      name = "pessoa_destinatario",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_feedback_pessoa_destinatario")
    )
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

    public Feedback atualizaCampos(Feedback novoFeedback) {
        if (novoFeedback.getComentario() != null) {
            this.setComentario(novoFeedback.getComentario());
        }
        if (novoFeedback.getNota() != null) {
            this.setNota(novoFeedback.getNota());
        }

        this.atualizadoEm = new Date();

        return this;
    }
}
