package br.com.easynutrition.domain.repository;

import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaloricExpenditureRepository extends JpaRepository<CaloricExpenditure, Long> {
    public CaloricExpenditure findAllByPersonId(Long id);
}
