package com.indexcrm.dto.integration;

// DTO para mapear o webhook da Evolution API (Text Message)
public class EvolutionTextMessageDTO {

    private String eventType;
    private DataPayload data;

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public DataPayload getData() { return data; }
    public void setData(DataPayload data) { this.data = data; }

    // --- CLASSE INTERNA ---
    public static class DataPayload {
        private KeyPayload key;
        private MessagePayload message;
        private String pushName;

        public KeyPayload getKey() { return key; }
        public void setKey(KeyPayload key) { this.key = key; }

        public MessagePayload getMessage() { return message; }
        public void setMessage(MessagePayload message) { this.message = message; }
        
        public String getPushName() { return pushName; }
        public void setPushName(String pushName) { this.pushName = pushName; }
    }

    public static class KeyPayload {
        private String remoteJid;
        private boolean fromMe;

        public String getRemoteJid() { return remoteJid; }
        public void setRemoteJid(String remoteJid) { this.remoteJid = remoteJid; }

        public boolean isFromMe() { return fromMe; }
        public void setFromMe(boolean fromMe) { this.fromMe = fromMe; }
    }

    public static class MessagePayload {
        private String conversation; // Texto simples

        public String getConversation() { return conversation; }
        public void setConversation(String conversation) { this.conversation = conversation; }
    }
}