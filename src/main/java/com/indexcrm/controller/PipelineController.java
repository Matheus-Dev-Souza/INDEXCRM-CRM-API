package com.indexcrm.controller;

import com.indexcrm.domain.sales.Pipeline;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.domain.user.User;
import com.indexcrm.repository.sales.PipelineRepository;
import com.indexcrm.repository.sales.PipelineStageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pipelines")
@Tag(name = "Funis de Venda", description = "Gerencia os funis (Pipelines) e suas fases (Stages)")
public class PipelineController {

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private PipelineStageRepository stageRepository;

    // 1. LISTAR FUNIS DA EMPRESA
    // Usado para preencher o select "Escolha o Funil" na Dashboard
    @GetMapping
    @Operation(summary = "Lista todos os funis da empresa do usuário logado")
    public ResponseEntity<List<Pipeline>> getPipelines(Authentication auth) {
        User user = (User) auth.getPrincipal();
        
        // Segurança: Só busca funis da empresa do usuário
        List<Pipeline> pipelines = pipelineRepository.findByCompanyId(user.getCompany().getId());
        
        return ResponseEntity.ok(pipelines);
    }

    // 2. CRIAR NOVO FUNIL
    @PostMapping
    @Operation(summary = "Cria um novo funil de vendas")
    public ResponseEntity<Pipeline> createPipeline(@RequestBody Pipeline pipelineData, Authentication auth) {
        User user = (User) auth.getPrincipal();

        // Vincula o funil à empresa do usuário
        pipelineData.setCompany(user.getCompany());
        
        // Se vier sem fases, podemos adicionar fases padrão aqui se quiser
        
        Pipeline saved = pipelineRepository.save(pipelineData);
        return ResponseEntity.ok(saved);
    }

    // 3. ADICIONAR UMA FASE AO FUNIL
    // Ex: Adicionar a coluna "Aguardando Assinatura"
    @PostMapping("/{id}/stages")
    @Operation(summary = "Adiciona uma nova fase a um funil existente")
    public ResponseEntity<PipelineStage> addStage(@PathVariable Long id, @RequestBody PipelineStage stageData, Authentication auth) {
        User user = (User) auth.getPrincipal();

        return pipelineRepository.findById(id)
                .map(pipeline -> {
                    // Segurança: Verifica se o funil pertence à empresa do usuário
                    if (!pipeline.getCompany().getId().equals(user.getCompany().getId())) {
                        throw new RuntimeException("Acesso negado a este funil.");
                    }

                    stageData.setPipeline(pipeline);
                    PipelineStage savedStage = stageRepository.save(stageData);
                    return ResponseEntity.ok(savedStage);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 4. DELETAR FUNIL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePipeline(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        
        pipelineRepository.findById(id).ifPresent(pipeline -> {
             if (pipeline.getCompany().getId().equals(user.getCompany().getId())) {
                 pipelineRepository.delete(pipeline);
             }
        });
        
        return ResponseEntity.noContent().build();
    }
}