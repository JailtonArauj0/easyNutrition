package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.Users.UsersRegisterDTO;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.domain.enums.UsersRole;
import br.com.easynutrition.domain.model.User.Users;
import br.com.easynutrition.domain.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    private Users userOne;
    private Users userTwo;
    private UsersRegisterDTO usersRegisterDTO;

    @BeforeEach
    void setUp() {
        userOne = Users
                .builder()
                .email("jailton-nf@hotmail.com")
                .password("test")
                .fullName("Jailton Araújo")
                .cpf("10606308524")
                .phone("83996088901")
                .usersRole(UsersRole.ADMIN)
                .build();

        userTwo = Users
                .builder()
                .email("dy.andriele150690@gmail.com")
                .password("test2")
                .fullName("Andriele Araújo")
                .cpf("70511016578")
                .phone("83999626657")
                .usersRole(UsersRole.USER)
                .build();

        usersRegisterDTO = UsersRegisterDTO
                .builder()
                .email("jailton-nf@hotmail.com")
                .password("test")
                .fullName("Jailton Araújo")
                .cpf("10606308524")
                .phone("83996088901")
                .usersRole(UsersRole.ADMIN)
                .build();
    }

    @Test
    void save_ifCpfAndEmailNotExist_createUser() {
        when(userRepository.findByEmailOrCpf(anyString(), anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        authorizationService.save(usersRegisterDTO);

        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void save_ifCpfExist_throwException() {
        when(userRepository.findByEmailOrCpf(anyString(), anyString())).thenReturn(Optional.of(userOne));

        CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> authorizationService.save(usersRegisterDTO)
        );

        Assertions.assertEquals("Este CPF já está em uso!", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void save_ifEmailExist_throwException() {
        when(userRepository.findByEmailOrCpf(anyString(), anyString())).thenReturn(Optional.of(userTwo));

        CustomException exception = Assertions.assertThrows(
                CustomException.class,
                () -> authorizationService.save(usersRegisterDTO)
        );

        Assertions.assertEquals("Este e-mail já está em uso!", exception.getMessage());

        verify(userRepository, never()).save(any());
    }
}