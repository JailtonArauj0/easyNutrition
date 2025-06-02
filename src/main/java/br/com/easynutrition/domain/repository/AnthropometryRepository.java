package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, Long> {

    List<Anthropometry> findAllByPersonId(Long id);

    List<Anthropometry> findAllByEvaluationDateAndPersonId(LocalDateTime date, Long id);
}
