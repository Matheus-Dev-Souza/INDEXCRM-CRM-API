package com.indexcrm.security;

import com.indexcrm.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscamos pelo EMAIL (pois é assim que definimos no UserRepository)
        UserDetails user = userRepository.findByEmail(email);

        // 2. Validação básica
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        }

        // 3. Como nossa entidade User já "implements UserDetails", 
        // podemos retorná-la diretamente. Não precisa converter manualmente.
        return user;
    }
}