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
@Table(name = "pagamento")
@EntityListeners(AuditingEntityListener.class)
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Float valor;

    private LocalDateTime dataHora;

    private String status;

    @OneToOne
    @JoinColumn(name = "leilao_id")
    private Leilao leilao;

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
