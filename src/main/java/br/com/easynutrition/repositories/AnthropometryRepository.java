package br.com.easynutrition.repositories;

import br.com.easynutrition.models.Anthropometry;
import br.com.easynutrition.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, Long> {

    List<Anthropometry> findAllByPersonId(Long id);

    List<Anthropometry> findAllByEvaluationDateAndPersonId(LocalDate date, Long id);

    /*
    @Query("SELECT a FROM Anthropometry a JOIN FETCH a.person")
    List<Anthropometry> findAllWithPerson(); */
}
