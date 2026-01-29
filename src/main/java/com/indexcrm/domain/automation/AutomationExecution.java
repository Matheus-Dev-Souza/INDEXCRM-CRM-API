package com.indexcrm.domain.automation;

import com.indexcrm.domain.sales.Lead;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_automation_executions")
@Data
public class AutomationExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Qual fluxo está rodando?
    @ManyToOne
    @JoinColumn(name = "flow_id")
    private AutomationFlow flow;

    // Qual lead está passando pelo fluxo?
    @ManyToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    private String status; // RUNNING, COMPLETED, FAILED, WAITING

    // Em qual passo do JSON o lead está parado agora? (ID do Node)
    private String currentStepId;

    @Column(columnDefinition = "TEXT")
    private String executionLogs; // Log de erros ou sucessos

    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime finishedAt;
}