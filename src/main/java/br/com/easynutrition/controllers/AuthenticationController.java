package br.com.easynutrition.controllers;

import br.com.easynutrition.configuration.security.TokenService;
import br.com.easynutrition.dtos.LoginResponseDTO;
import br.com.easynutrition.dtos.UsersDTO;
import br.com.easynutrition.dtos.UsersRegisterDTO;
import br.com.easynutrition.exception.CustomException;
import br.com.easynutrition.models.Users;
import br.com.easynutrition.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UsersDTO usersDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usersDTO.getEmail(), usersDTO.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsersRegisterDTO usersRegisterDTO) {
        if (userRepository.findByEmail(usersRegisterDTO.getEmail()).isPresent()) {
            throw new CustomException("Este e-mail já está em uso");
        }
        if (userRepository.findByCpf(usersRegisterDTO.getCpf()).isPresent()) {
            throw new CustomException("Este CPF já está em uso!");
        }
        Users user = new Users();
        BeanUtils.copyProperties(usersRegisterDTO, user);
        user.setPassword(new BCryptPasswordEncoder().encode(usersRegisterDTO.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}