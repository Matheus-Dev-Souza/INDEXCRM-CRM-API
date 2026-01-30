package com.indexcrm.service.sales;

import com.indexcrm.domain.crm.Lead;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.domain.user.User;
import com.indexcrm.dto.request.CreateLeadDTO;
import com.indexcrm.repository.sales.LeadRepository;
import com.indexcrm.repository.sales.PipelineStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private PipelineStageRepository stageRepository;

    @Transactional
    public Lead createLead(CreateLeadDTO data, User owner) {
        // 1. Valida a Fase
        PipelineStage stage = stageRepository.findById(data.stageId())
                .orElseThrow(() -> new RuntimeException("Fase não encontrada"));

        // 2. Valida Segurança (Multi-tenant)
        // Nota: Aqui comparamos String com String (UUIDs), então está correto
        if (!stage.getPipeline().getCompany().getId().equals(owner.getCompany().getId())) {
            throw new SecurityException("Você não tem permissão nesta fase.");
        }

        // 3. Monta o Lead
        Lead lead = new Lead();
        lead.setTitle(data.title());
        lead.setDescription(data.description());
        lead.setValue(data.value());
        lead.setPriority(data.priority());
        lead.setCustomerName(data.name());
        lead.setEmail(data.email());
        lead.setPhone(data.phone());

        lead.setStage(stage);
        lead.setOwner(owner);
        lead.setCompany(owner.getCompany());

        // Datas automáticas
        lead.setCreatedAt(LocalDateTime.now());
        lead.setUpdatedAt(LocalDateTime.now());

        return leadRepository.save(lead);
    }

    // CORREÇÃO AQUI: Mudamos de (Long companyId) para (String companyId)
    public List<Lead> getLeadsByCompany(String companyId) {
        return leadRepository.findByCompanyId(companyId);
    }
}