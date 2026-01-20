package com.indexcrm.dto.response;

public record LoginResponseDTO(
    String token,
    String type,      // "Bearer"
    UserResponseDTO user // Retornamos os dados do usuário para o Front já salvar no contexto
) {
    // Construtor de conveniência
    public LoginResponseDTO(String token, UserResponseDTO user) {
        this(token, "Bearer", user);
    }
}