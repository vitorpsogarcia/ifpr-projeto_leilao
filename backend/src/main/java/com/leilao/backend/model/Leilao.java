package com.leilao.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.leilao.backend.enums.StatusLeilao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do leilão é obrigatório")
    @Size(min = 5, max = 150, message = "O título do leilão deve ter entre 5 e 150 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição do leilão é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @Lob
    private String descricaoDetalhada;

    @Future(message = "A data de início do leilão deve ser no futuro")
    private LocalDateTime dataHoraInicio;

    @Future(message = "A data de fim do leilão deve ser no futuro")
    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    private StatusLeilao status;

    @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
    private String observacao;

    @Positive(message = "O valor de incremento deve ser positivo")
    private Float valorIncremento;

    @PositiveOrZero(message = "O lance mínimo deve ser positivo ou zero")
    private Float lanceMinimo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference("pessoa-leiloes")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "leilao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("leilao-imagens")
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "leilao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("leilao-lances")
    private List<Lance> lances;

    @OneToOne(mappedBy = "leilao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("leilao-pagamento")
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
