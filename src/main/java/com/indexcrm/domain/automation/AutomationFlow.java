package com.indexcrm.domain.automation;

import com.indexcrm.domain.saas.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_automation_flows")
@Data
public class AutomationFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Boolean active = false; // Se está ligado ou desligado

    // O Gatilho que inicia o fluxo (Ex: LEAD_CREATED, STAGE_CHANGED)
    private String triggerEvent;

    // O JSON gigante que guarda os blocos visuais do seu fluxo (Nodes e Edges)
    @Column(columnDefinition = "TEXT")
    private String flowDefinitionJson;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}