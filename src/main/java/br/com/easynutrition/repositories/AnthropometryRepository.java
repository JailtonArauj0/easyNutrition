package br.com.easynutrition.repositories;

import br.com.easynutrition.models.Anthropometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, Long> {

    List<Anthropometry> findAllByPersonId(Long id);

    List<Anthropometry> findAllByEvaluationDateAndPersonId(LocalDate date, Long id);
}
