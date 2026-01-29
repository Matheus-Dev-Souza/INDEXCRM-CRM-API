package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.PipelineStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineStageRepository extends JpaRepository<PipelineStage, Long> {
    // Útil para buscar fases de um funil ordenadas pela ordem (index)
    List<PipelineStage> findByPipelineIdOrderByIndexOrderAsc(Long pipelineId);
}