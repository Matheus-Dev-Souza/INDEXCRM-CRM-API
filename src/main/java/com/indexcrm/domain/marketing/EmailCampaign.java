package com.indexcrm.domain.marketing;

import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_email_campaigns")
@Data
public class EmailCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject; // Assunto do E-mail

    @Column(columnDefinition = "TEXT")
    private String bodyHtml; // Conteúdo HTML

    private String status; // DRAFT, SCHEDULED, SENDING, SENT

    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;

    // Métricas
    private Integer sentCount;
    private Integer openCount;  // Taxa de Abertura
    private Integer clickCount; // Taxa de Clique

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private LocalDateTime createdAt = LocalDateTime.now();
}