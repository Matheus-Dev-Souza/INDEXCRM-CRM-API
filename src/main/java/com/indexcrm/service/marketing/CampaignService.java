package com.indexcrm.service.marketing;

import com.indexcrm.domain.marketing.EmailCampaign;
import com.indexcrm.integration.EmailProvider;
import com.indexcrm.repository.marketing.EmailCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampaignService {

    @Autowired
    private EmailCampaignRepository emailCampaignRepository;

    @Autowired
    private EmailProvider emailProvider;

    @Autowired
    private WhatsAppService whatsAppService; // Reutilizamos o serviço acima

    /**
     * Dispara uma campanha de E-mail pendente
     */
    @Transactional
    public void sendEmailCampaign(Long campaignId) {
        EmailCampaign campaign = emailCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada"));

        // Lógica simplificada: Enviar para um teste (em produção, percorreria a lista de leads)
        try {
            // Supondo que você tenha um método para buscar leads alvo...
            // Por enquanto, enviamos um teste para o criador
            String targetEmail = campaign.getAuthor().getEmail();
            
            emailProvider.sendHtmlEmail(targetEmail, campaign.getSubject(), campaign.getBodyHtml());

            campaign.setStatus("SENT");
            campaign.setSentCount(1); // Exemplo
            emailCampaignRepository.save(campaign);

        } catch (Exception e) {
            campaign.setStatus("FAILED");
            emailCampaignRepository.save(campaign);
            throw e;
        }
    }

    /**
     * Exemplo de envio de SMS/WhatsApp em massa
     */
    public void sendWhatsAppBlast(String message, java.util.List<String> phones) {
        for (String phone : phones) {
            // Pequeno delay para não bloquear o chip (boa prática)
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            
            whatsAppService.sendMessage(phone, message);
        }
    }
}