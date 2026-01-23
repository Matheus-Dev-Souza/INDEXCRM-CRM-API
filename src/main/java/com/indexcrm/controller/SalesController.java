package com.indexcrm.controller;

import com.indexcrm.domain.crm.Lead;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.domain.user.User;
import com.indexcrm.dto.request.CreateLeadDTO;
import com.indexcrm.repository.sales.LeadRepository;
import com.indexcrm.repository.sales.PipelineStageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private PipelineStageRepository stageRepository;

    // 1. CRIAR UM NOVO LEAD (Com dados do Negócio + Cliente)
    @PostMapping("/leads")
    public ResponseEntity<?> createLead(@RequestBody @Valid CreateLeadDTO data, Authentication auth) {
        User user = (User) auth.getPrincipal(); // Pega o usuário logado

        // Busca a fase onde o lead vai entrar (Ex: "Novo Lead")
        PipelineStage stage = stageRepository.findById(data.stageId())
                .orElseThrow(() -> new RuntimeException("Fase não encontrada"));

        // Segurança: Verifica se a fase pertence à empresa do usuário
        if (!stage.getPipeline().getCompany().getId().equals(user.getCompany().getId())) {
            return ResponseEntity.status(403).body("Você não pode adicionar leads nesta fase.");
        }

        Lead lead = new Lead();

        // --- Mapeando dados do NEGÓCIO ---
        lead.setTitle(data.title());
        lead.setDescription(data.description());
        lead.setValue(data.value());
        lead.setPriority(data.priority());

        // --- Mapeando dados do CLIENTE (Novos campos) ---
        lead.setCustomerName(data.name()); // O DTO manda "name", salvamos como "customerName"
        lead.setEmail(data.email());
        lead.setPhone(data.phone());

        // --- Vinculos ---
        lead.setStage(stage);
        lead.setOwner(user);
        lead.setCompany(user.getCompany()); // Vincula à empresa do usuário logado

        // Atualiza datas
        lead.setCreatedAt(LocalDateTime.now());
        lead.setUpdatedAt(LocalDateTime.now());

        leadRepository.save(lead);

        return ResponseEntity.ok(lead);
    }

    // 2. LISTAR TODOS OS LEADS DA MINHA EMPRESA
    @GetMapping("/leads")
    public ResponseEntity<List<Lead>> getMyLeads(Authentication auth) {
        User user = (User) auth.getPrincipal();

        // Busca apenas leads da empresa que está logada (Segurança Multi-tenant)
        List<Lead> leads = leadRepository.findByCompanyId(user.getCompany().getId());

        return ResponseEntity.ok(leads);
    }
}