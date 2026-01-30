package com.indexcrm.integration;

public interface WhatsAppClient {

    /**
     * Envia uma mensagem de texto simples.
     * @param phone Número no formato internacional (Ex: 5511999999999)
     * @param message Texto da mensagem
     */
    void sendText(String phone, String message);
}