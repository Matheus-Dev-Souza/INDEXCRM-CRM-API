package com.indexcrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateLeadDTO(
        // --- DADOS DO CONTATO (Pessoa) ---
        @NotBlank(message = "O nome do cliente é obrigatório")
        String name, // Ex: "João Silva"

        @Email(message = "Email inválido")
        String email, // Ex: "joao@gmail.com"

        @NotBlank(message = "O telefone/whatsapp é obrigatório")
        String phone, // Ex: "5511999999999" (Importante para sua integração com WhatsApp)

        // --- DADOS DA OPORTUNIDADE (Negócio) ---
        @NotBlank(message = "O título do card é obrigatório")
        String title, // Ex: "Venda de Site Institucional"

        String description, // Detalhes do que foi conversado

        @NotNull(message = "O valor é obrigatório")
        BigDecimal value, // Quanto vale essa venda?

        String priority, // "HIGH", "MEDIUM", "LOW"

        @NotBlank(message = "O ID da fase (stage) é obrigatório")
        String stageId // O UUID da coluna onde o card vai entrar
) {}