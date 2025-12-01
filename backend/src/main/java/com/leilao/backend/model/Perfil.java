package com.leilao.backend.model;

import com.leilao.backend.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "perfil")
@EntityListeners(AuditingEntityListener.class)
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;

    @OneToMany(mappedBy = "perfil")
    private List<PessoaPerfil> pessoaPerfil;

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
