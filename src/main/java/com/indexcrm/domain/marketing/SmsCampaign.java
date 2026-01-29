package com.indexcrm.domain.marketing;

import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_sms_campaigns")
@Data
public class SmsCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nome interno da campanha

    @Column(columnDefinition = "TEXT")
    private String messageContent; // O texto do SMS (max 160 chars geralmente)

    private String status; // DRAFT, SCHEDULED, SENDING, COMPLETED, FAILED

    private LocalDateTime scheduledAt; // Quando deve ser enviado
    private LocalDateTime sentAt;      // Quando foi efetivamente enviado

    // Métricas simples
    private Integer targetCount;       // Quantos leads na lista
    private Integer successCount;      // Quantos receberam
    private Integer failureCount;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private LocalDateTime createdAt = LocalDateTime.now();
}