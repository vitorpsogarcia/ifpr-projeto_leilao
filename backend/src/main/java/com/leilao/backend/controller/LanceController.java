package com.leilao.backend.controller;

import com.leilao.backend.model.Lance;
import com.leilao.backend.service.LanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lances")
public class LanceController {

    @Autowired
    private LanceService service;

    @PostMapping
    public ResponseEntity<Lance> criar(@RequestBody Lance lance) {
        return ResponseEntity.ok(service.criar(lance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lance> buscarPorId(@PathVariable Long id) {
        Lance lance = service.buscarPorId(id);
        if (lance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lance);
    }

    @GetMapping
    public ResponseEntity<List<Lance>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lance> atualizar(@PathVariable Long id, @RequestBody Lance lance) {
        Lance lanceAtualizado = service.atualizar(id, lance);
        if (lanceAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lanceAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}

