package com.indexcrm.controller;

import com.indexcrm.domain.automation.AutomationFlow;
import com.indexcrm.domain.user.User;
import com.indexcrm.repository.automation.AutomationFlowRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automations")
@Tag(name = "Automação", description = "Gerencia os fluxos de automação (Visual Builder)")
public class AutomationController {

    @Autowired
    private AutomationFlowRepository flowRepository;

    // 1. LISTAR AUTOMACÕES DA EMPRESA
    @GetMapping
    @Operation(summary = "Lista todos os fluxos de automação da empresa")
    public ResponseEntity<List<AutomationFlow>> getFlows(Authentication auth) {
        User user = (User) auth.getPrincipal();

        List<AutomationFlow> flows = flowRepository.findByCompanyId(user.getCompany().getId());
        return ResponseEntity.ok(flows);
    }

    // 2. PEGAR UM FLUXO ESPECÍFICO (Para abrir no Editor Visual)
    @GetMapping("/{id}")
    public ResponseEntity<AutomationFlow> getFlow(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();

        return flowRepository.findById(id)
                .map(flow -> {
                    // Segurança: Verifica se o fluxo é da empresa do usuário
                    if (!flow.getCompany().getId().equals(user.getCompany().getId())) {
                        throw new RuntimeException("Acesso negado a este fluxo.");
                    }
                    return ResponseEntity.ok(flow);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CRIAR NOVO FLUXO
    @PostMapping
    @Operation(summary = "Cria um novo fluxo de automação")
    public ResponseEntity<AutomationFlow> createFlow(@RequestBody AutomationFlow flowData, Authentication auth) {
        User user = (User) auth.getPrincipal();

        flowData.setCompany(user.getCompany());
        flowData.setActive(false); // Começa desligado por segurança
        
        // Se não vier JSON (criação rápida), inicializa vazio
        if (flowData.getFlowDefinitionJson() == null) {
            flowData.setFlowDefinitionJson("{}");
        }

        AutomationFlow saved = flowRepository.save(flowData);
        return ResponseEntity.ok(saved);
    }

    // 4. ATUALIZAR FLUXO (Salvar o desenho do JSON)
    @PutMapping("/{id}")
    @Operation(summary = "Salva as alterações do fluxo (JSON visual)")
    public ResponseEntity<AutomationFlow> updateFlow(@PathVariable Long id, @RequestBody AutomationFlow flowData, Authentication auth) {
        User user = (User) auth.getPrincipal();

        return flowRepository.findById(id)
                .map(existingFlow -> {
                    if (!existingFlow.getCompany().getId().equals(user.getCompany().getId())) {
                        throw new RuntimeException("Acesso negado.");
                    }

                    // Atualiza campos
                    if (flowData.getName() != null) existingFlow.setName(flowData.getName());
                    if (flowData.getTriggerEvent() != null) existingFlow.setTriggerEvent(flowData.getTriggerEvent());
                    if (flowData.getActive() != null) existingFlow.setActive(flowData.getActive());
                    
                    // O CAMPO MAIS IMPORTANTE: O JSON DO VISUAL BUILDER
                    if (flowData.getFlowDefinitionJson() != null) {
                        existingFlow.setFlowDefinitionJson(flowData.getFlowDefinitionJson());
                    }

                    AutomationFlow updated = flowRepository.save(existingFlow);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETAR FLUXO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlow(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();

        flowRepository.findById(id).ifPresent(flow -> {
            if (flow.getCompany().getId().equals(user.getCompany().getId())) {
                flowRepository.delete(flow);
            }
        });

        return ResponseEntity.noContent().build();
    }
}