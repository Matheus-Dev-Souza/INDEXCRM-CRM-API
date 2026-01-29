package com.indexcrm.controller;

import com.indexcrm.dto.integration.EvolutionTextMessageDTO;
import com.indexcrm.service.integration.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhooks")
@Tag(name = "Webhooks", description = "Endpoints para integração com serviços externos (WhatsApp, Stripe, etc)")
public class WebhookController {

    @Autowired
    private WebhookService webhookService;

    /**
     * Recebe mensagens do WhatsApp via Evolution API.
     * URL para configurar na Evolution: https://sua-api.onrender.com/webhooks/whatsapp
     */
    @PostMapping("/whatsapp")
    @Operation(summary = "Recebe eventos da Evolution API")
    public ResponseEntity<Void> handleWhatsAppEvent(@RequestBody Map<String, Object> payload) {
        // Usamos Map<String, Object> aqui porque o payload da Evolution pode variar muito
        // (Text, Image, Audio, Status Update, etc).
        // O Service vai decidir como tratar.
        webhookService.processWhatsAppEvent(payload);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Recebe eventos de pagamento (Ex: Stripe ou Asaas).
     * URL para configurar no Gateway: https://sua-api.onrender.com/webhooks/payment
     */
    @PostMapping("/payment")
    @Operation(summary = "Recebe atualizações de pagamento (Assinaturas SaaS)")
    public ResponseEntity<Void> handlePaymentEvent(
            @RequestBody String payload, 
            @RequestHeader(value = "Stripe-Signature", required = false) String signature) {
        
        webhookService.processPaymentEvent(payload, signature);
        
        return ResponseEntity.ok().build();
    }
}