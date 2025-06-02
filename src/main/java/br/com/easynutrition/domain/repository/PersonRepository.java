package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.model.Person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByCpf(String cpf);

    @Query("SELECT p.birthDate FROM Person p WHERE p.id = :anthropometryId")
    Person getAge(@Param("anthropometryId") Long id);
}
