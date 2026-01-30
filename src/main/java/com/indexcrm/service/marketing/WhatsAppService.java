package com.indexcrm.service.marketing;

import com.indexcrm.integration.WhatsAppClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Autowired
    private WhatsAppClient whatsAppClient;

    /**
     * Envia mensagem para um número específico.
     * Faz validações antes de chamar a integração externa.
     */
    public void sendMessage(String phone, String message) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("O telefone é obrigatório");
        }

        // Limpeza básica do telefone (remove espaços, traços, parenteses)
        String cleanPhone = phone.replaceAll("[^0-9]", "");

        // Exemplo: Adicionar 55 se não tiver (Regra de negócio BR)
        if (cleanPhone.length() <= 11 && !cleanPhone.startsWith("55")) {
            cleanPhone = "55" + cleanPhone;
        }

        whatsAppClient.sendText(cleanPhone, message);
    }
}