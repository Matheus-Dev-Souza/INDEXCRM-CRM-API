package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.PipelineStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipelineStageRepository extends JpaRepository<PipelineStage, String> {
    // Agora o .save() existe automaticamente
}