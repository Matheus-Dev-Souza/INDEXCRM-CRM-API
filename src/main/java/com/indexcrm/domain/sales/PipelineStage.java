package com.indexcrm.domain.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indexcrm.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "pipeline_stages")
public class PipelineStage extends BaseEntity {

    private String name;
    
    // CORREÇÃO: Mudamos de 'orderIndex' para 'indexOrder'
    // para bater com o nome que o Repositório está buscando.
    private Integer indexOrder; 

    private String color;   // Cor da coluna (Ex: #FF0000)

    @ManyToOne
    @JoinColumn(name = "pipeline_id")
    @JsonIgnore // <--- IMPORTANTE: Adicionei isso para evitar erro de Loop Infinito no JSON
    private Pipeline pipeline;

    // --- GETTERS E SETTERS ATUALIZADOS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(Integer indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}