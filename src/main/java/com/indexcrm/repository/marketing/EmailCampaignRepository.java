package com.indexcrm.repository.marketing;

import com.indexcrm.domain.marketing.EmailCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailCampaignRepository extends JpaRepository<EmailCampaign, Long> {
    // Busca campanhas por empresa (para listagem)
    List<EmailCampaign> findByCompanyId(Long companyId);
    
    // Busca campanhas pendentes de envio (para o disparador automático)
    List<EmailCampaign> findByStatus(String status);
}