package com.indexcrm.repository.company;

import com.indexcrm.domain.saas.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // 1. Verificação de Duplicidade (Usado no Registro)
    // Impede que duas pessoas cadastrem o mesmo CNPJ/CPF
    boolean existsByDocument(String document);

    // 2. Busca por Documento
    Optional<Company> findByDocument(String document);

    // 3. Busca por ID do Stripe/Gateway (Essencial para Webhooks)
    // Quando o Stripe avisa "Pagamento recebido do cliente cus_123", 
    // usamos esse método para saber qual empresa liberar.
    Optional<Company> findByStripeCustomerId(String stripeCustomerId);
}