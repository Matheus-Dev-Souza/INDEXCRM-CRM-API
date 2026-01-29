package com.indexcrm.repository.automation;

import com.indexcrm.domain.automation.AutomationFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationFlowRepository extends JpaRepository<AutomationFlow, Long> {
    
    // Busca todos os fluxos de uma empresa específica
    List<AutomationFlow> findByCompanyId(Long companyId);
}