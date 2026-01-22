package com.indexcrm.controller; // <--- OBRIGATÓRIO SER A PRIMEIRA LINHA

import com.indexcrm.dto.request.MarketingDTO;
import com.indexcrm.service.marketing.MarketingService;
import jakarta.validation.Valid; // <--- CORREÇÃO: Usar Jakarta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/marketing")
public class MarketingController {

    private final MarketingService marketingService;

    @Autowired
    public MarketingController(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GetMapping
    public ResponseEntity<List<MarketingDTO>> findAll() {
        return ResponseEntity.ok(marketingService.findAll());
    }

    // Correção: ID deve ser String (UUID), não Long
    @GetMapping("/{id}")
    public ResponseEntity<MarketingDTO> findById(@PathVariable String id) {
        return marketingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MarketingDTO> create(@Valid @RequestBody MarketingDTO dto) {
        MarketingDTO created = marketingService.create(dto);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id()) // Correção: Record usa .id() e não .getId()
                .toUri();
                
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarketingDTO> update(@PathVariable String id, @Valid @RequestBody MarketingDTO dto) {
        return marketingService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (marketingService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}