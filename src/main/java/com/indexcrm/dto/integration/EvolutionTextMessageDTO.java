package com.indexcrm.dto.integration;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos extras que a Evolution manda e a gente não usa
public class EvolutionTextMessageDTO {

    private String type; // Ex: "messages.upsert"
    private String instance; // Qual instância (número) recebeu
    private DataPayload data;
    private String sender; // As vezes vem direto na raiz

    // --- CLASSES INTERNAS PARA MAPEAMENTO DO JSON ---

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataPayload {
        private Key key;
        private String pushName; // Nome de exibição do contato (Ex: "Matheus")
        private MessageContent message;
        private String messageType; // Ex: "conversation", "extendedTextMessage"
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Key {
        private String remoteJid; // O número do cliente (Ex: 5588999...@s.whatsapp.net)
        private boolean fromMe;   // true = eu mandei, false = cliente mandou
        private String id;        // ID único da mensagem
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageContent {
        // Caso 1: Texto simples
        private String conversation;
        
        // Caso 2: Texto com link ou formatação
        @JsonAlias("extendedTextMessage") // Aceita vir com esse nome também
        private ExtendedTextMessage extendedTextMessage;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExtendedTextMessage {
        private String text; // O texto real quando é extended
    }

    // --- MÉTODO UTILITÁRIO (O PULO DO GATO) ---
    // O WhatsApp manda texto em lugares diferentes dependendo se é iPhone, Android ou se tem link.
    // Esse método resolve isso e te devolve o texto limpo.
    public String extractMessageText() {
        if (data == null || data.getMessage() == null) {
            return "";
        }

        // Tenta pegar texto simples (conversation)
        if (data.getMessage().getConversation() != null && !data.getMessage().getConversation().isEmpty()) {
            return data.getMessage().getConversation();
        }

        // Tenta pegar texto estendido (com links/negrito)
        if (data.getMessage().getExtendedTextMessage() != null) {
            return data.getMessage().getExtendedTextMessage().getText();
        }

        return ""; // Se for áudio, imagem ou sticker, retorna vazio por enquanto
    }
    
    // Método para pegar o telefone formatado (sem o @s.whatsapp.net)
    public String extractRemoteJid() {
        if (data != null && data.getKey() != null) {
            return data.getKey().getRemoteJid();
        }
        return null;
    }
}