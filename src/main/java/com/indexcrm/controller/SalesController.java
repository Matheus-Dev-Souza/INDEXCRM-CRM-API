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

    @PostMapping("/leads")
    public ResponseEntity<?> createLead(@RequestBody @Valid CreateLeadDTO data, Authentication auth) {
        User user = (User) auth.getPrincipal();

        // Agora data.stageId() é Long, então findById aceita tranquilamente
        PipelineStage stage = stageRepository.findById(data.stageId())
                .orElseThrow(() -> new RuntimeException("Fase não encontrada"));

        // Verificação de Segurança (IDs da Company comparados como Strings)
        if (!stage.getPipeline().getCompany().getId().equals(user.getCompany().getId())) {
            return ResponseEntity.status(403).body("Você não pode adicionar leads nesta fase.");
        }

        Lead lead = new Lead();
        lead.setTitle(data.title());
        lead.setDescription(data.description());
        lead.setValue(data.value());
        lead.setPriority(data.priority());
        lead.setCustomerName(data.name());
        lead.setEmail(data.email());
        lead.setPhone(data.phone());
        lead.setStage(stage);
        lead.setOwner(user);
        lead.setCompany(user.getCompany());
        lead.setCreatedAt(LocalDateTime.now());
        lead.setUpdatedAt(LocalDateTime.now());

        leadRepository.save(lead);

        return ResponseEntity.ok(lead);
    }

    @GetMapping("/leads")
    public ResponseEntity<List<Lead>> getMyLeads(Authentication auth) {
        User user = (User) auth.getPrincipal();

        // Agora findByCompanyId aceita String, então user.getCompany().getId() funciona
        List<Lead> leads = leadRepository.findByCompanyId(user.getCompany().getId());

        return ResponseEntity.ok(leads);
    }
}