package com.indexcrm.dto;

public record CompanyResponseDTO(
    String id,
    String name,
    String planType,
    boolean active
) {}