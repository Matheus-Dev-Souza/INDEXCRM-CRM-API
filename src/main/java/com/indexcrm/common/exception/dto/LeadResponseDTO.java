package com.indexcrm.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeadResponseDTO(
    String id,
    String name,
    String email,
    String phone,
    String statusColumn,
    BigDecimal dealValue,
    String ownerName,       // Apenas o nome do vendedor, não o objeto User inteiro
    LocalDateTime createdAt
) {}