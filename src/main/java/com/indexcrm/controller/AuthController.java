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
    private UserRepository repository; 
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PipelineService pipelineService; 

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        UserResponseDTO userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, userResponse));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        // 1. Cria a Empresa (Multi-tenant)
        Company newCompany = new Company();
        
        String companyName = (data.companyName() != null && !data.companyName().isEmpty()) 
                             ? data.companyName() 
                             : data.name() + " Company";
                             
        newCompany.setName(companyName);
        newCompany.setPlanType("FREE");
        newCompany.setActive(true);
        // Gera um slug temporário (em produção, faríamos algo mais robusto)
        newCompany.setSlug(companyName.toLowerCase().replace(" ", "-") + "-" + System.currentTimeMillis());
        
        // --- CORREÇÃO AQUI: ---
        // Atribuímos o resultado do save à variável 'savedCompany'
        Company savedCompany = this.companyRepository.save(newCompany);

        // 2. Cria o Usuário vinculado à Empresa SALVA
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // Usamos 'savedCompany' aqui para garantir que o ID já existe
        User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.ADMIN, savedCompany);

        this.repository.save(newUser);
        
        // 3. Cria o Funil de Vendas Padrão usando o ID da empresa salva
        pipelineService.createDefaultPipeline(savedCompany.getId());

        return ResponseEntity.ok().build();
    }
}