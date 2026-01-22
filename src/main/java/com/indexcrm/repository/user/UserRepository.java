package com.indexcrm.repository.user;

import com.indexcrm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    // Método mágico do Spring Data: 
    // Ele cria o SQL "SELECT * FROM users WHERE email = ?" sozinho
    UserDetails findByEmail(String email);
}