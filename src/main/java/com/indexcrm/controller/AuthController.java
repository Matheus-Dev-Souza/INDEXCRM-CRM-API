package com.indexcrm.controller;

import com.indexcrm.domain.saas.Company;
import com.indexcrm.domain.user.User;
import com.indexcrm.domain.user.UserRole;
import com.indexcrm.dto.request.LoginRequestDTO;
import com.indexcrm.dto.request.RegisterRequestDTO;
import com.indexcrm.dto.response.LoginResponseDTO;
import com.indexcrm.dto.response.UserResponseDTO;
import com.indexcrm.mapper.UserMapper;
import com.indexcrm.repository.saas.CompanyRepository;
import com.indexcrm.repository.user.UserRepository;
import com.indexcrm.service.TokenService;
import com.indexcrm.service.sales.PipelineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository repository; // Repositório de Usuário
    
    @Autowired
    private CompanyRepository companyRepository; // Repositório de Empresa
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UserMapper userMapper; // Para converter User -> UserResponseDTO
    
    @Autowired
    private PipelineService pipelineService; // Para criar o Kanban Padrão

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        // 1. Autentica usuário e senha (o Spring Security faz a mágica aqui)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 2. Pega o usuário autenticado
        User user = (User) auth.getPrincipal();

        // 3. Gera o Token
        var token = tokenService.generateToken(user);

        // 4. Cria o DTO de resposta com os dados do usuário (correção do erro 'userDto')
        UserResponseDTO userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, userResponse));
    }

    @PostMapping("/register")
    @Transactional // Garante que cria tudo (Empresa + User + Funil) ou nada
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        // 1. Cria a Empresa (Multi-tenant)
        Company newCompany = new Company();
        // Usa o nome da empresa se vier no DTO, senão usa o nome do usuário + Company
        String companyName = (data.companyName() != null && !data.companyName().isEmpty()) 
                             ? data.companyName() 
                             : data.name() + " Company";
                             
        newCompany.setName(companyName);
        newCompany.setPlanType("FREE"); // Plano padrão
        newCompany.setActive(true);
        
        this.companyRepository.save(newCompany);

        // 2. Cria o Usuário vinculado à Empresa
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // Construtor atualizado com a Company
        User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.ADMIN, newCompany);

        this.repository.save(newUser);
        
        // 3. (Importante) Cria o Funil de Vendas Padrão para essa nova empresa
        pipelineService.createDefaultPipeline(newCompany);

        return ResponseEntity.ok().build();
    }
}