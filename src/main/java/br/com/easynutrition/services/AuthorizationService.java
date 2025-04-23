package br.com.easynutrition.services;

import br.com.easynutrition.dtos.request.UsersRegisterDTO;
import br.com.easynutrition.enums.UsersRole;
import br.com.easynutrition.exception.CustomException;
import br.com.easynutrition.models.Users;
import br.com.easynutrition.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}