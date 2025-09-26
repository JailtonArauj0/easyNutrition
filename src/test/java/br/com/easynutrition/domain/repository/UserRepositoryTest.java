package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.enums.UsersRole;
import br.com.easynutrition.domain.model.User.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        List<Users> usersList = new ArrayList<>();
        Users userOne = Users
                .builder()
                .email("jailton-nf@hotmail.com")
                .password("test")
                .fullName("Jailton Araújo")
                .cpf("10606308524")
                .phone("83996088901")
                .usersRole(UsersRole.ADMIN)
                .build();

        Users userTwo = Users
                .builder()
                .email("dy.andriele150690@gmail.com")
                .password("test2")
                .fullName("Andriele Araújo")
                .cpf("70511013485")
                .phone("83999626657")
                .usersRole(UsersRole.USER)
                .build();

        usersList.add(userOne);
        usersList.add(userTwo);

        userRepository.saveAll(usersList);
    }

    @Test
    void findByEmail_ifEmailExist_returnUser() {
        String email = "jailton-nf@hotmail.com";
        Users user = userRepository.findByEmail(email).orElse(null);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    void findByCpf_ifCpfExist_returnUser() {
        String cpf = "70511013485";
        Users user = userRepository.findByCpf(cpf).orElse(null);
        assertNotNull(user);
        assertEquals(cpf, user.getCpf());
    }

    @Test
    void findByEmailOrCpf_ifCpfOrEmailExist_returnUser() {
        String email = "jailton-nf@hotmail.com";
        String cpf = "70511085426";
        Users user = userRepository.findByEmailOrCpf(email, cpf).orElse(null);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertNotEquals("70511085426", user.getCpf());
    }
}