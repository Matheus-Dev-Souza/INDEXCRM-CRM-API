package com.indexcrm.repository.marketing;

import com.indexcrm.domain.marketing.SmsCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsCampaignRepository extends JpaRepository<SmsCampaign, Long> {
    List<SmsCampaign> findByCompanyId(Long companyId);
    List<SmsCampaign> findByStatus(String status);
}