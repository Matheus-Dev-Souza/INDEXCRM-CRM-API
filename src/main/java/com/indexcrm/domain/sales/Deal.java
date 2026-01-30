package com.indexcrm.domain.sales;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deals")
public class Deal extends BaseEntity {

    private String title;
    private BigDecimal amount; // O erro chamava isso de 'amount' ou 'value'
    private String status;     // WON, LOST, OPEN

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // --- GETTERS E SETTERS ---

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}