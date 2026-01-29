package com.indexcrm.repository.user;

import com.indexcrm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    // Método padrão já existente
    User findByEmail(String email);

    // --- ADICIONE ESTE NOVO: ---
    List<User> findByCompanyId(String companyId);
}