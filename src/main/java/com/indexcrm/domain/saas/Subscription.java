package com.indexcrm.domain.saas;

import com.indexcrm.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_subscriptions")
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento 1 para 1: Uma empresa tem uma assinatura ativa
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String status; // ACTIVE, PAST_DUE, CANCELED, TRIAL

    private LocalDate startDate;
    private LocalDate nextBillingDate;

    private String stripeSubscriptionId; // ID externo (Gateway de Pagamento)

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status) || "TRIAL".equalsIgnoreCase(this.status);
    }
}