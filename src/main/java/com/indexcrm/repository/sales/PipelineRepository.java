package com.indexcrm.repository.sales;

import com.indexcrm.domain.sales.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, Long> {
    List<Pipeline> findByCompanyId(Long companyId);
}