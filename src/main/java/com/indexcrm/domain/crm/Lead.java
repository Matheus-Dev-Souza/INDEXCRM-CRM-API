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

    // --- DADOS DO NEGÓCIO ---
    private String title;
    private String description;
    private BigDecimal value;
    private String priority;

    // --- DADOS DO CLIENTE (Novos campos) ---
    private String customerName; // Renomeei para customerName para não confundir com title
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private PipelineStage stage;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // --- GETTERS E SETTERS ---

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    // Getters/Setters dos Novos Campos
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public PipelineStage getStage() { return stage; }
    public void setStage(PipelineStage stage) { this.stage = stage; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}