package com.indexcrm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// Use 'record' para DTOs (Java 17+)
public record CreateLeadDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O email é obrigatório")
        String email,

        String phone,

        String title,
        String description,
        BigDecimal value,
        String priority,

        @NotNull(message = "O ID da fase é obrigatório")
        Long stageId // <--- GARANTA QUE ISTO SEJA LONG (Não String)
) {}