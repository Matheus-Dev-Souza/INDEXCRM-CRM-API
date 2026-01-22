package com.indexcrm.service.sales;

import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.sales.Pipeline;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.repository.sales.PipelineRepository;
import com.indexcrm.repository.sales.PipelineStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PipelineService {

    @Autowired
    private PipelineRepository pipelineRepository;
    
    @Autowired
    private PipelineStageRepository stageRepository;

    // --- ESTE É O MÉTODO QUE FALTAVA ---
    @Transactional
    public void createDefaultPipeline(Company company) {
        Pipeline pipeline = new Pipeline();
        pipeline.setName("Funil de Vendas Padrão");
        pipeline.setCompany(company);
        
        pipelineRepository.save(pipeline);

        createStage(pipeline, "Novo Lead", 0, "#3b82f6");
        createStage(pipeline, "Contato Feito", 1, "#eab308");
        createStage(pipeline, "Proposta", 2, "#f97316");
        createStage(pipeline, "Ganho", 3, "#22c55e");
        createStage(pipeline, "Perdido", 4, "#ef4444");
    }

    private void createStage(Pipeline p, String name, int order, String color) {
        PipelineStage stage = new PipelineStage();
        stage.setName(name);
        stage.setOrderIndex(order);
        stage.setColor(color);
        stage.setPipeline(p);
        stageRepository.save(stage);
    }
}