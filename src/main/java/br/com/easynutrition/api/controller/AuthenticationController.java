package br.com.easynutrition.api.controller;

import br.com.easynutrition.infra.configuration.security.TokenService;
import br.com.easynutrition.api.dto.LoginResponseDTO;
import br.com.easynutrition.api.dto.request.Users.UsersDTO;
import br.com.easynutrition.api.dto.request.Users.UsersRegisterDTO;
import br.com.easynutrition.domain.model.User.Users;
import br.com.easynutrition.domain.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UsersDTO usersDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usersDTO.getEmail(), usersDTO.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersRegisterDTO usersRegisterDTO) {
        authorizationService.save(usersRegisterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}