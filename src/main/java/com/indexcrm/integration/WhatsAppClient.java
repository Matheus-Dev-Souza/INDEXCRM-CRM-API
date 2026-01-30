package com.indexcrm.service.marketing;

import com.indexcrm.integration.WhatsAppClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    // ALTERAÇÃO: required = false para não quebrar se não tiver config
    @Autowired(required = false)
    private WhatsAppClient whatsAppClient;

    public void sendMessage(String phone, String message) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("O telefone é obrigatório");
        }

        // Se não tiver cliente de WhatsApp configurado, apenas loga e sai
        if (whatsAppClient == null) {
            System.out.println("⚠️ AVISO: WhatsApp não enviado (Integração não configurada).");
            System.out.println("Para: " + phone + " | Mensagem: " + message);
            return;
        }

        String cleanPhone = phone.replaceAll("[^0-9]", "");
        if (cleanPhone.length() <= 11 && !cleanPhone.startsWith("55")) {
            cleanPhone = "55" + cleanPhone;
        }

        whatsAppClient.sendText(cleanPhone, message);
    }
}