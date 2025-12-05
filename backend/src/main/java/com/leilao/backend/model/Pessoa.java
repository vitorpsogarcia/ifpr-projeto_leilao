package com.leilao.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.leilao.backend.enums.TipoPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "pessoa")
@EntityListeners(AuditingEntityListener.class)
public class Pessoa implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    private String nome;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.notvalid}")
    private String email;

    @JsonIgnore
    private String senha;

    @JsonIgnore
    @Column(unique = true, name = "cpf", length = 11, nullable = false)
    private String cpf;

    @JsonIgnore
    @Column(unique = true, name = "codigo_validacao", length = 8)
    private String codigoValidacao;

    @Column(name = "validade_codigo_validacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validadeCodigoValidacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "BLOB")
    private byte[] fotoPerfil;

    @ElementCollection(targetClass = TipoPerfil.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "pessoa_perfis", joinColumns = @JoinColumn(name = "pessoa_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Set<TipoPerfil> perfis = new HashSet<>(Collections.singletonList(TipoPerfil.COMPRADOR));

    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "escritor",
      targetEntity =  Feedback.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    private List<Feedback> feedbacksEscritos;


    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "destinatario",
      targetEntity =  Feedback.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    private List<Feedback> feedbacksDestinatario;

    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "pessoa",
      targetEntity =  Categoria.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    private List<Categoria> categorias;

    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "pessoa",
      targetEntity =  Leilao.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    @JsonManagedReference("pessoa-leiloes")
    private List<Leilao> leiloes;

    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "pessoa",
      targetEntity =  Lance.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    @JsonIgnore
    private List<Lance> lances;

    @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "pessoa",
      targetEntity =  Feedback.class,
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    @JsonManagedReference("pessoa-feedbacks")
    private List<Feedback> feedbacks;

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority("ROLE_" + perfil.name()))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
