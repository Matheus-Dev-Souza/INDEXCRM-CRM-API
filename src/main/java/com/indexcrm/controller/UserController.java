package com.indexcrm.controller;

import com.indexcrm.domain.user.User;
import com.indexcrm.dto.response.UserResponseDTO;
import com.indexcrm.repository.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Gerenciamento de perfil e equipe")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 1. OBTER DADOS DO USUÁRIO LOGADO (ME)
    // O Front chama isso no carregamento para mostrar "Olá, Matheus"
    @GetMapping("/me")
    @Operation(summary = "Retorna o perfil do usuário logado")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        
        // Recarrega do banco para garantir dados atualizados
        return userRepository.findById(user.getId())
                .map(u -> ResponseEntity.ok(mapToDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. LISTAR EQUIPE (Usuários da mesma empresa)
    @GetMapping
    @Operation(summary = "Lista todos os membros da equipe da empresa")
    public ResponseEntity<List<UserResponseDTO>> getMyTeam(Authentication auth) {
        User currentUser = (User) auth.getPrincipal();

        // Busca apenas usuários da mesma empresa
        List<User> team = userRepository.findByCompanyId(currentUser.getCompany().getId());

        List<UserResponseDTO> dtos = team.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 3. ATUALIZAR PERFIL
    @PutMapping("/me")
    @Operation(summary = "Atualiza dados do perfil (Nome/Email)")
    public ResponseEntity<UserResponseDTO> updateProfile(@RequestBody UserResponseDTO data, Authentication auth) {
        User user = (User) auth.getPrincipal();
        User userToUpdate = userRepository.findById(user.getId()).orElseThrow();

        if (data.name() != null) userToUpdate.setName(data.name());
        // Se mudar e-mail, cuidado: o login vai mudar.
        if (data.email() != null) userToUpdate.setEmail(data.email());

        userRepository.save(userToUpdate);

        return ResponseEntity.ok(mapToDTO(userToUpdate));
    }

    // --- Método Auxiliar de Conversão ---
    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole().toString(),
            user.getCompany() != null ? user.getCompany().getName() : "Sem Empresa"
        );
    }
}