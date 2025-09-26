package br.com.easynutrition.api.controller;

import br.com.easynutrition.api.dto.LoginResponseDTO;
import br.com.easynutrition.api.dto.request.Users.UsersDTO;
import br.com.easynutrition.api.dto.request.Users.UsersRegisterDTO;
import br.com.easynutrition.domain.model.User.Users;
import br.com.easynutrition.domain.service.AuthorizationService;
import br.com.easynutrition.domain.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UsersDTO usersDTO, HttpServletRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usersDTO.getEmail(), usersDTO.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal(), request);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        authorizationService.logout(authHeader);

        return ResponseEntity.ok(authorizationService.logout(authHeader));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UsersRegisterDTO usersRegisterDTO) {
        authorizationService.save(usersRegisterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}