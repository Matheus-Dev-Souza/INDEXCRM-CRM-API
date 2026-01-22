package com.indexcrm.mapper;

import com.indexcrm.domain.user.User;
import com.indexcrm.dto.response.CompanyResponseDTO;
import com.indexcrm.dto.response.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Converte Entidade User -> DTO de Resposta
    public UserResponseDTO toResponse(User entity) {
        if (entity == null) return null;

        // Cria o DTO da empresa (se existir)
        CompanyResponseDTO companyDto = null;
        if (entity.getCompany() != null) {
            companyDto = new CompanyResponseDTO(
                entity.getCompany().getId(),
                entity.getCompany().getName(),
                entity.getCompany().getPlanType(),
                entity.getCompany().isActive()
            );
        }

        return new UserResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getRole().getRole(), // Pega a string "admin" ou "user"
            companyDto
        );
    }
}