package com.leilao.backend.model;

import com.leilao.backend.enums.StatusLeilao;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "leilao")
@EntityListeners(AuditingEntityListener.class)
public class Leilao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    private String descricao;

    @Lob
    private String descricaoDetalhada;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    private StatusLeilao status;

    private String observacao;

    private Float valorIncremento;

    private Float lanceMinimo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "leilao")
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "leilao")
    private List<Lance> lances;

    @OneToOne(mappedBy = "leilao")
    private Pagamento pagamento;

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
