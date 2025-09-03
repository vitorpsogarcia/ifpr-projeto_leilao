package com.leilao.backend.controller;

import com.leilao.backend.dto.FeedbackDTO;
import com.leilao.backend.model.Feedback;
import com.leilao.backend.model.Feedback;
import com.leilao.backend.service.FeedbackService;
import com.leilao.backend.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<Page<Feedback>> buscarTodos(Pageable pageable) {

        return ResponseEntity.ok(feedbackService.buscarTodos(pageable));
    }

    @PostMapping
    public ResponseEntity<Feedback> inserir(@Valid @RequestBody FeedbackDTO feedback) {
        return ResponseEntity.ok(feedbackService.inserir(feedback));
    }

    @PutMapping
    public ResponseEntity<Feedback> alterar(@Valid @RequestBody Feedback feedback) {
        return ResponseEntity.ok(feedbackService.alterar(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        feedbackService.excluir(id);
        return ResponseEntity.ok("Excluído");
    }

}
