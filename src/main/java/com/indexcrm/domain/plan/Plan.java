package com.indexcrm.domain.plan;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_plans")
@Data
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ex: "Plano Pro"

    private String description;

    private BigDecimal price; // Valor mensal

    // --- Limites do Plano ---
    private Integer maxUsers; // Quantos usuários a empresa pode ter
    private Integer maxLeads; // Quantos leads podem cadastrar
    private Integer maxPipelines;

    private Boolean active = true;
}