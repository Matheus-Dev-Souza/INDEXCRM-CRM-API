package com.indexcrm.service.auth; // 1. Package sempre na linha 1

import com.indexcrm.domain.user.User;
import com.indexcrm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService; // Usamos o serviço que já criamos para gerar tokens

    /**
     * Autentica as credenciais e retorna um JWT assinado.
     * Lança AuthenticationException se falhar.
     */
    public String authenticateAndGetToken(String email, String password) {
        // 1. Cria o objeto de autenticação (Email + Senha)
        UsernamePasswordAuthenticationToken loginData = 
                new UsernamePasswordAuthenticationToken(email, password);

        // 2. O Spring Security verifica a senha no banco automaticamente
        Authentication auth = authenticationManager.authenticate(loginData);

        // 3. Se passou, pegamos o usuário logado
        User user = (User) auth.getPrincipal();

        // 4. Geramos o token usando nosso TokenService (com biblioteca Auth0)
        return tokenService.generateToken(user);
    }
}