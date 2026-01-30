package com.indexcrm.mapper;

import com.indexcrm.domain.user.User;
import com.indexcrm.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Converte Entidade User -> DTO de Resposta
    public UserResponseDTO toResponse(User entity) {
        if (entity == null) return null;

        // CORREÇÃO: Pegamos apenas o NOME da empresa (String), não o objeto todo.
        String companyName = (entity.getCompany() != null) ? entity.getCompany().getName() : null;

        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole().getRole(), // Pega a string "admin" ou "user"
                companyName // <--- Agora enviamos String, compatível com o DTO novo
        );
    }
}