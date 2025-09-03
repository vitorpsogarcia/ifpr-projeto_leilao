package com.leilao.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FeedbackDTO {
    private Long id;
    private String comentario;
    private Integer nota;
    private Date dataHora;
    private Long idEscritor;
    private Long idDestinatario;
}
