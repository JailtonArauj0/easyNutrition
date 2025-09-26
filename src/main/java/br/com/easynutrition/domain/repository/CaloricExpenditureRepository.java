package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaloricExpenditureRepository extends JpaRepository<CaloricExpenditure, Long> {
    List<CaloricExpenditure> findAllByPersonId(Long id);
}
