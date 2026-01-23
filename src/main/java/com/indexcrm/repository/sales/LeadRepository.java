package com.indexcrm.repository.sales;

import com.indexcrm.domain.crm.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, String> {

    // Buscar todos os leads de uma empresa específica (Segurança Multi-tenant)
    List<Lead> findByCompanyId(String companyId);

    // Buscar leads de uma fase específica (para montar a coluna do Kanban)
    List<Lead> findByStageId(String stageId);
}