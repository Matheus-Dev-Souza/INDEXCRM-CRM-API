package com.indexcrm.service.integration;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class WebhookService {

    public void processWhatsAppEvent(Map<String, Object> payload) {
        System.out.println("📩 Webhook WhatsApp recebido: " + payload);
        
        // Lógica futura:
        // 1. Verificar se é uma mensagem de texto (type == "text")
        // 2. Extrair o telefone do remetente (remoteJid)
        // 3. Buscar se o Lead existe. Se não, criar.
        // 4. Salvar a mensagem no histórico do Lead.
    }

    public void processPaymentEvent(String payload, String signature) {
        System.out.println("💰 Webhook Pagamento recebido");
        
        // Lógica futura:
        // 1. Verificar assinatura de segurança (Stripe Signature)
        // 2. Ler o evento (Ex: invoice.payment_succeeded)
        // 3. Atualizar o status da Subscription da Company para ACTIVE.
    }
}