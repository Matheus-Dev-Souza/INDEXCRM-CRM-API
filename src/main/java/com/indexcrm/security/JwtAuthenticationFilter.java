package com.indexcrm.security; // 1. O Package DEVE ser a primeira linha

import com.indexcrm.domain.user.User;
import com.indexcrm.repository.user.UserRepository;
import com.indexcrm.service.TokenService;
import jakarta.servlet.FilterChain; // 2. Usando Jakarta em vez de Javax
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private TokenService tokenService; // Adaptado para usar nosso serviço existente

    @Autowired
    private UserRepository userRepository; // Adaptado para usar nosso repositório existente

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = resolveToken(request);
            
            // Lógica adaptada: Nosso TokenService retorna o email (String), não boolean
            if (jwt != null) {
                String userEmail = tokenService.validateToken(jwt);
                
                // Se retornou um email válido e não está autenticado ainda
                if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    User userDetails = (User) userRepository.findByEmail(userEmail);
                    
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Falha ao autenticar o usuário pelo token JWT: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}