package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    // CORREÇÃO: Mudamos de (Long companyId) para (String companyId)
    // Pois o ID da Company agora é um UUID (String)
    List<Lead> findByCompanyId(String companyId);
}