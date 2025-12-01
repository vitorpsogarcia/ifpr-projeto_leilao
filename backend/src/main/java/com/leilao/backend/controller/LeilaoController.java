package com.leilao.backend.controller;

import com.leilao.backend.model.Leilao;
import com.leilao.backend.service.LeilaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leiloes")
public class LeilaoController {

    @Autowired
    private LeilaoService service;

    @PostMapping
    public ResponseEntity<Leilao> criar(@RequestBody Leilao leilao) {
        return ResponseEntity.ok(service.criar(leilao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leilao> buscarPorId(@PathVariable Long id) {
        Leilao leilao = service.buscarPorId(id);
        if (leilao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leilao);
    }

    @GetMapping
    public ResponseEntity<List<Leilao>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leilao> atualizar(@PathVariable Long id, @RequestBody Leilao leilao) {
        Leilao leilaoAtualizado = service.atualizar(id, leilao);
        if (leilaoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leilaoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}

