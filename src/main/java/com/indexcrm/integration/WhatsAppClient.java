package com.indexcrm.integration;

public interface WhatsAppClient {
    /**
     * Envia mensagem de texto via integração.
     */
    void sendText(String phone, String message);
}