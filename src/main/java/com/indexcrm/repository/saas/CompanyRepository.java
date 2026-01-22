package com.indexcrm.repository.saas;

import com.indexcrm.domain.saas.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    // Agora o método .save() vai funcionar automaticamente
}