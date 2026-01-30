package com.indexcrm.dto.response;

public record UserResponseDTO(
        String id,          // UUID do usuário
        String name,        // Nome
        String email,       // Email
        String role,        // Cargo (ADMIN, USER)
        String companyName  // <--- AQUI ESTAVA O ERRO. Agora aceita texto!
) {}