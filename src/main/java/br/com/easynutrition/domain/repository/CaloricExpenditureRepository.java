package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaloricExpenditureRepository extends JpaRepository<CaloricExpenditure, Long> {
    Optional<List<CaloricExpenditure>> findAllByPersonId(Long id);
}
