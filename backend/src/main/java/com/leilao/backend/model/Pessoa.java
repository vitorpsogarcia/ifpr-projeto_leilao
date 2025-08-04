package com.leilao.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "pessoa")
@EntityListeners(AuditingEntityListener.class)
public class Pessoa implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    private String nome;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.notvalid}")
    private String email;

    @JsonIgnore
    private String senha;

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

    @OneToMany(
      fetch = FetchType.EAGER,
      mappedBy = "pessoa",
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    @Setter(AccessLevel.NONE)
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

    public void setPessoaPerfil(List<PessoaPerfil> pessoaPerfil) {
        for (PessoaPerfil p : pessoaPerfil) {
            p.setPessoa(this);
        }

        this.pessoaPerfil = pessoaPerfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return pessoaPerfil
          .stream()
          .map(
            perfil -> new SimpleGrantedAuthority(
              perfil
                .getPerfil().getTipo().name()
            )
          )
          .collect(
            Collectors.toList()
          );
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
