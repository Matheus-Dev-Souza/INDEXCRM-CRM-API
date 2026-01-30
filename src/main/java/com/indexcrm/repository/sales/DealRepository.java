package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByCompanyId(Long companyId);
}