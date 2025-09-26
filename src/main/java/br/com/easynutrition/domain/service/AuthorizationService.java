package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.Users.UsersRegisterDTO;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.domain.enums.UsersRole;
import br.com.easynutrition.domain.model.User.Users;
import br.com.easynutrition.domain.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthorizationService(UserRepository userRepository, TokenBlacklistService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void save(UsersRegisterDTO usersRegisterDTO) {
        Optional<Users> userExist = userRepository.findByEmailOrCpf(usersRegisterDTO.getEmail(), usersRegisterDTO.getCpf());

        userExist.ifPresent(
                user -> {
                    String message = user.getCpf().equals(usersRegisterDTO.getCpf()) ? "Este CPF já está em uso!" : "Este e-mail já está em uso!";
                    throw new CustomException(message);
                }
        );

        Users user = Users.builder()
                .email(usersRegisterDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(usersRegisterDTO.getPassword()))
                .fullName(usersRegisterDTO.getFullName())
                .cpf(usersRegisterDTO.getCpf())
                .phone(usersRegisterDTO.getPhone())
                .usersRole(usersRegisterDTO.getUsersRole() == null ? UsersRole.USER : usersRegisterDTO.getUsersRole())
                .registrationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        userRepository.save(user);
    }

    public Map<String, String> logout(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException("Bearer token não fornecido no header.");
        }

        try {
            DecodedJWT jwt = JWT.decode(authHeader.replace("Bearer ", ""));
            String tokenId = jwt.getId();

            tokenBlacklistService.revokeToken(tokenId, authHeader);

            return Map.of(
                    "message", "Acesso revogado com sucesso."
            );

        } catch (Exception e) {
            throw new CustomException("Token inválido.");
        }
    }
}