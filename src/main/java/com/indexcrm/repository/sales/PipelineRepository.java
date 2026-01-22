package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, String> {
    // Agora o .save() existe automaticamente
}