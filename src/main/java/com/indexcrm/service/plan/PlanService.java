package com.indexcrm.service.plan;

import com.indexcrm.domain.plan.Plan;
import com.indexcrm.repository.plan.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public List<Plan> getAllActivePlans() {
        return planRepository.findByActiveTrue();
    }

    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }



}