package com.indexcrm.service.marketing;

import com.indexcrm.dto.request.MarketingDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarketingService {

    // --- MÉTODOS MOCK (Para compilar e testar a API) ---
    // Futuramente, aqui você chamará o repository.save()

    public List<MarketingDTO> findAll() {
        return Collections.emptyList();
    }

    public Optional<MarketingDTO> findById(String id) {
        // Simula busca
        return Optional.of(new MarketingDTO(id, "Campanha Teste", "EMAIL", "Olá!", "DRAFT"));
    }

    public MarketingDTO create(MarketingDTO dto) {
        // Simula criação e gera ID
        return new MarketingDTO(UUID.randomUUID().toString(), dto.name(), dto.type(), dto.content(), "DRAFT");
    }

    public Optional<MarketingDTO> update(String id, MarketingDTO dto) {
        return Optional.of(new MarketingDTO(id, dto.name(), dto.type(), dto.content(), "DRAFT"));
    }

    public boolean delete(String id) {
        return true;
    }
}