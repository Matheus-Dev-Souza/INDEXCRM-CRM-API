package com.indexcrm.domain.user;

import com.indexcrm.domain.BaseEntity;
import com.indexcrm.domain.saas.Company;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    // --- 1. DEFINIÇÃO DAS VARIÁVEIS (CAMPOS) ---
    // Sem isso, o "this.email" dava erro
    
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // --- 2. CONSTRUTORES ---

    // Construtor vazio (Obrigatório para o JPA/Hibernate)
    public User() {
    }

    // Construtor completo (Para facilitar na hora de criar)
    public User(String name, String email, String password, UserRole role, Company company) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.company = company;
    }

    // --- 3. GETTERS E SETTERS MANUAIS (Para não depender do Lombok) ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // --- 4. MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY (UserDetails) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return email; // Usamos o email como login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}