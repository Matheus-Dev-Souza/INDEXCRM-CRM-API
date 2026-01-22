package com.indexcrm.domain.user;

public enum UserRole {
    
    ADMIN("admin"),
    USER("user");

    private String role;

    // Construtor do Enum
    UserRole(String role){
        this.role = role;
    }

    // Getter manual (para recuperar o texto "admin" ou "user")
    public String getRole(){
        return role;
    }
}