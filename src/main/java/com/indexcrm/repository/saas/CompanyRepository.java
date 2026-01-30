package com.indexcrm.repository.saas;

import com.indexcrm.domain.saas.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// ATENÇÃO: Se sua Company usa UUID, mantemos <Company, String>. 
// Se usar ID numérico (1, 2, 3), troque String por Long na linha abaixo.
public interface CompanyRepository extends JpaRepository<Company, String> {

    // --- Métodos de Verificação (Usados no Registro) ---
    
    boolean existsByDocument(String document);

    Optional<Company> findByDocument(String document);

    // --- Métodos de Integração (Usados nos Webhooks) ---
    
    Optional<Company> findByStripeCustomerId(String stripeCustomerId);
}