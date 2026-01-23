package com.indexcrm.domain.crm;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.sales.PipelineStage;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "leads")
public class Lead extends BaseEntity {

    private String title;       // Ex: "Venda de Site para Padaria"
    private String description;
    private BigDecimal value;   // Valor da venda (Ex: 5000.00)
    private String priority;    // LOW, MEDIUM, HIGH

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private PipelineStage stage; // Em qual coluna do Kanban ele está?

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; // Quem é o vendedor responsável?

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company; // De qual empresa é esse lead?

    // --- GETTERS E SETTERS MANUAIS ---

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public PipelineStage getStage() { return stage; }
    public void setStage(PipelineStage stage) { this.stage = stage; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}