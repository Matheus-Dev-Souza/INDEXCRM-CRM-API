package com.indexcrm.domain.saas;

import com.indexcrm.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String document; // CPF ou CNPJ

    // --- NOVO: Campo obrigatório para o erro sumir ---
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId; 

    // --- NOVO: Campo para URL amigável (ex: /app/minha-agencia) ---
    @Column(unique = true)
    private String slug; 

    private String ownerId;
    
    private boolean active = true;
    
    private String planType; // FREE, PRO, ENTERPRISE

    // --- GETTERS E SETTERS ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    // O Getter/Setter que o Spring estava procurando e não achava
    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }
}