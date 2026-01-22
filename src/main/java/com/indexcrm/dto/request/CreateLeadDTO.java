package com.indexcrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record CreateLeadDTO(
    @NotBlank(message = "O nome do lead é obrigatório")
    String name,

    @Email
    String email,

    @NotBlank(message = "O telefone/whatsapp é obrigatório")
    String phone,

    BigDecimal dealValue, // Valor da negociação
    
    String statusColumn   // Ex: "NEW", "IN_PROGRESS", "WON"
) {}