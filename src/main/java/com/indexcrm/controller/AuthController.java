package com.indexcrm.controller;
import com.indexcrm.dto.request.LoginRequestDTO;
import com.indexcrm.dto.request.RegisterRequestDTO;
import com.indexcrm.dto.response.LoginResponseDTO;
// ...
import com.indexcrm.domain.user.User;
import com.indexcrm.domain.user.UserRepository;
import com.indexcrm.domain.user.UserRole;
import com.indexcrm.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, userDto));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.USER);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
@PostMapping("/register")
@Transactional // Importante: Ou salva tudo ou não salva nada
public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
    if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

    // 1. Criar a Empresa primeiro
    Company newCompany = new Company();
    newCompany.setCorporateName(data.name() + " Company"); // Nome temporário
    newCompany.setSubscriptionActive(true); // Começa ativa (Trial ou Free)
    this.companyRepository.save(newCompany);

    // 2. Criar o Usuário vinculado à Empresa
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    
    // Note que agora passamos a newCompany no construtor
    User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.ADMIN, newCompany);

    this.repository.save(newUser);

    return ResponseEntity.ok().build();
}

// DTOs auxiliares
record LoginDTO(String email, String password) {}
record RegisterDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password, UserRole role) {}
record LoginResponseDTO(String token) {}