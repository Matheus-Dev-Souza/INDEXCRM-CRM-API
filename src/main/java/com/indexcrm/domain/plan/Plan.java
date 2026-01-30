package com.indexcrm.domain.plan;

import com.indexcrm.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "plans")
public class Plan extends BaseEntity {

    private String name;
    private BigDecimal price;
    private String description;
    private int maxUsers;
    private int maxLeads;
    private boolean active;

    // --- GETTERS E SETTERS OBRIGATÓRIOS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getMaxLeads() {
        return maxLeads;
    }

    public void setMaxLeads(int maxLeads) {
        this.maxLeads = maxLeads;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}