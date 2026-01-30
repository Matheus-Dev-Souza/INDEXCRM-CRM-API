package com.indexcrm.service.sales;

import com.indexcrm.domain.sales.Deal;
import com.indexcrm.domain.user.User;
import com.indexcrm.repository.sales.DealRepository; // Certifique-se de criar este repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private DealRepository dealRepository;

    /**
     * Cria uma oportunidade de venda (Deal)
     */
    public Deal createDeal(Deal deal, User owner) {
        deal.setOwner(owner);
        deal.setCompany(owner.getCompany());
        
        // Define status inicial se não vier
        if (deal.getStatus() == null) {
            deal.setStatus("OPEN");
        }
        
        return dealRepository.save(deal);
    }

    /**
     * Calcula o valor total do Pipeline (Dashboard)
     */
    public BigDecimal calculatePipelineValue(Long companyId) {
        List<Deal> deals = dealRepository.findByCompanyId(companyId); // Precisa ter esse método no repo
        
        return deals.stream()
                .filter(d -> "OPEN".equals(d.getStatus())) // Só soma o que está aberto
                .map(Deal::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}