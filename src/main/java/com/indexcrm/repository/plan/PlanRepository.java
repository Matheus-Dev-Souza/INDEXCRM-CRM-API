package com.indexcrm.repository.plan;

import com.indexcrm.domain.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    
    // Método mágico do Spring Data: Monta a query "WHERE active = true" sozinho
    List<Plan> findByActiveTrue();
}