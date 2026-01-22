package com.indexcrm.domain.sales;

import com.indexcrm.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "pipeline_stages")
public class PipelineStage extends BaseEntity {

    private String name;
    private int orderIndex; // A ordem da coluna no Kanban (0, 1, 2...)
    private String color;   // Cor da coluna (Ex: #FF0000)

    @ManyToOne
    @JoinColumn(name = "pipeline_id")
    private Pipeline pipeline;

    // --- GETTERS E SETTERS MANUAIS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
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