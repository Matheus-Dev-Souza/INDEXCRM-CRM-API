package com.indexcrm.controller;

import com.indexcrm.domain.plan.Plan;
import com.indexcrm.repository.plan.PlanRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@Tag(name = "Planos (SaaS)", description = "Gerenciamento dos planos de assinatura")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    // 1. LISTAR TODOS OS PLANOS (Público ou Logado)
    // Usado na tela de "Escolha seu plano" ou "Upgrade"
    @GetMapping
    @Operation(summary = "Lista todos os planos ativos")
    public ResponseEntity<List<Plan>> listPlans() {
        // Retorna apenas os planos marcados como ativos
        List<Plan> plans = planRepository.findByActiveTrue();
        return ResponseEntity.ok(plans);
    }

    // 2. OBTER DETALHES DE UM PLANO
    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable Long id) {
        return planRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CRIAR NOVO PLANO (Admin)
    // Ex: Criar o plano "Black Friday" via API
    @PostMapping
    @Operation(summary = "Cria um novo plano (Apenas Admin)")
    // @PreAuthorize("hasRole('ADMIN')") // Descomente se tiver roles configuradas
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan savedPlan = planRepository.save(plan);
        return ResponseEntity.ok(savedPlan);
    }

    // 4. ATUALIZAR PLANO
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano existente")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan planData) {
        return planRepository.findById(id)
                .map(existingPlan -> {
                    existingPlan.setName(planData.getName());
                    existingPlan.setPrice(planData.getPrice());
                    existingPlan.setDescription(planData.getDescription());
                    existingPlan.setMaxUsers(planData.getMaxUsers());
                    existingPlan.setMaxLeads(planData.getMaxLeads());
                    existingPlan.setActive(planData.getActive());
                    
                    Plan updated = planRepository.save(existingPlan);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}