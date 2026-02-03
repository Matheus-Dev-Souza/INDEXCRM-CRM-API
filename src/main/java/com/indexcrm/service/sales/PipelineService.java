package com.indexcrm.service.sales;

import com.indexcrm.domain.sales.Pipeline;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.repository.sales.PipelineRepository;
import com.indexcrm.repository.sales.PipelineStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PipelineService {

    @Autowired
    private PipelineRepository pipelineRepository;

    @Autowired
    private PipelineStageRepository stageRepository;

    @Transactional
    public Pipeline createDefaultPipeline(String companyId) {
        // 1. Cria o Pipeline (Funil)
        Pipeline pipeline = new Pipeline();
        pipeline.setName("Funil de Vendas Padrão");
        // pipeline.setCompanyId(companyId); // Se tiver esse campo, descomente
        
        // Salva o pai primeiro
        Pipeline savedPipeline = pipelineRepository.save(pipeline);

        // 2. Cria as Fases Padrão (Stages)
        createStage(savedPipeline, "Novo Lead", 1, "#3498db");       // Azul
        createStage(savedPipeline, "Qualificado", 2, "#f1c40f");     // Amarelo
        createStage(savedPipeline, "Em Negociação", 3, "#e67e22");   // Laranja
        createStage(savedPipeline, "Ganho", 4, "#2ecc71");           // Verde
        createStage(savedPipeline, "Perdido", 5, "#e74c3c");         // Vermelho

        return savedPipeline;
    }

    private void createStage(Pipeline pipeline, String name, int order, String color) {
        PipelineStage stage = new PipelineStage();
        stage.setName(name);
        stage.setPipeline(pipeline);
        
        // CORREÇÃO: Mudamos de setOrderIndex para setIndexOrder
        stage.setIndexOrder(order); 
        
        stage.setColor(color);
        stageRepository.save(stage);
    }
}