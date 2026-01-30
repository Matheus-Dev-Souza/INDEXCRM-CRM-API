package com.indexcrm.domain.automation;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.saas.Company;
import jakarta.persistence.*;

@Entity
@Table(name = "automation_flows")
public class AutomationFlow extends BaseEntity {

    private String name;
    private String triggerEvent; // Ex: LEAD_CREATED, DEAL_WON
    private boolean active;

    @Column(columnDefinition = "TEXT")
    private String flowDefinitionJson; // O desenho do fluxo em JSON

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // --- GETTERS E SETTERS OBRIGATÓRIOS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFlowDefinitionJson() {
        return flowDefinitionJson;
    }

    public void setFlowDefinitionJson(String flowDefinitionJson) {
        this.flowDefinitionJson = flowDefinitionJson;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
// ... outros getters e setters ...

    // ADICIONE ESTE MÉTODO:
    public boolean getActive() {
        return active;
    }

    // Mantenha o setActive
    public void setActive(boolean active) {
        this.active = active;
    }
}