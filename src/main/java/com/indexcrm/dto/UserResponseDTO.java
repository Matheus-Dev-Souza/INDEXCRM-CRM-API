package com.indexcrm.dto;

public record UserResponseDTO(
    String id,
    String name,
    String email,
    String role,
    CompanyResponseDTO company // Dados resumidos da empresa dele
) {}