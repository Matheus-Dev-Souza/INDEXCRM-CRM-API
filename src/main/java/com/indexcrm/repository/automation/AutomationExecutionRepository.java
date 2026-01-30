package com.indexcrm.repository.automation;

import com.indexcrm.domain.automation.AutomationExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationExecutionRepository extends JpaRepository<AutomationExecution, Long> {

    // 1. Buscar histórico de um Fluxo específico
    // Usado na tela de detalhes da automação para mostrar "Últimas execuções"
    List<AutomationExecution> findByFlowIdOrderByStartedAtDesc(Long flowId);

    // 2. Buscar histórico de um Lead específico
    // Usado na Timeline do Lead para mostrar "Recebeu SMS", "Mudou de Fase", etc.
    List<AutomationExecution> findByLeadIdOrderByStartedAtDesc(Long leadId);

    // 3. Buscar por Status (Essencial para o Robô/Scheduler)
    // Ex: O robô busca tudo que está "RUNNING" ou "WAITING" para continuar o processo
    List<AutomationExecution> findByStatus(String status);

    // 4. Buscar todas as execuções de uma Empresa (Segurança/Dashboard)
    // Como a Execution não tem companyId direto (está dentro do Flow), fazemos um Join
    @Query("SELECT e FROM AutomationExecution e WHERE e.flow.company.id = :companyId ORDER BY e.startedAt DESC")
    List<AutomationExecution> findByCompanyId(@Param("companyId") Long companyId);
}