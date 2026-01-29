package com.indexcrm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AutomationFlowDTO {

    @NotBlank(message = "O nome do fluxo é obrigatório")
    private String name;

    private String description;

    @NotBlank(message = "O gatilho é obrigatório")
    private String triggerEvent; // Ex: LEAD_CREATED, DEAL_WON

    private Boolean active;

    // O campo mais importante: O JSON com os nós e conexões do desenho
    // Não validamos @NotBlank aqui pois podemos salvar um rascunho vazio
    private String flowDefinitionJson;
}