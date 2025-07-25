package br.com.easynutrition.repositories;

import br.com.easynutrition.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByCpf(String cpf);

    Optional<Users> findByEmailOrCpf(String email, String cpf);
}