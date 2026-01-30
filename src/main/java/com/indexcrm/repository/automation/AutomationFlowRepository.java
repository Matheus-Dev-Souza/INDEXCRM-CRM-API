package com.indexcrm.repository.automation;

import com.indexcrm.domain.automation.AutomationFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationFlowRepository extends JpaRepository<AutomationFlow, Long> {

    // CORREÇÃO: Mudamos de (Long companyId) para (String companyId)
    // para bater com o ID da sua Empresa que é UUID.
    List<AutomationFlow> findByCompanyId(String companyId);
}