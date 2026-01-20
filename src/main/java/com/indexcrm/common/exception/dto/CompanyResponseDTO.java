package com.indexcrm.dto.response;

public record CompanyResponseDTO(
    String id,
    String name,
    String planType,
    boolean active
) {}