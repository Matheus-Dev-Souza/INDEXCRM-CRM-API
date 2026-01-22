package com.indexcrm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MarketingDTO(
    String id, // UUID é String
    
    @NotBlank(message = "O nome da campanha é obrigatório")
    String name,
    
    String type, // "EMAIL", "SMS", "WHATSAPP"
    String content,
    String status // "DRAFT", "SENT"
) {}