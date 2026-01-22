package com.indexcrm.domain.sales;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.saas.Company;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pipelines")
public class Pipeline extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL)
    private List<PipelineStage> stages;

    // --- GETTERS E SETTERS MANUAIS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<PipelineStage> getStages() {
        return stages;
    }

    public void setStages(List<PipelineStage> stages) {
        this.stages = stages;
    }
}