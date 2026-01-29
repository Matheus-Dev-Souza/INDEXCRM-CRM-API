package com.indexcrm.domain.sales;

import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_leads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Dados do Negócio ---
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal value; // Melhor usar BigDecimal para dinheiro

    private String priority; // LOW, MEDIUM, HIGH

    // --- Dados do Cliente (Contato) ---
    @Column(name = "customer_name")
    private String customerName; // O Controller espera este nome exato

    private String email;

    private String phone;

    // --- Relacionamentos ---

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private PipelineStage stage; // A fase do funil (Ex: Novo, Em Negociação)

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; // Quem criou o lead (Usuario logado)

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company; // Multi-tenant: O lead pertence a uma empresa

    // --- Auditoria ---
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}