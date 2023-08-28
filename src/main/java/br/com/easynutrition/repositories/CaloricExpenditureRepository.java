package br.com.easynutrition.repositories;

import br.com.easynutrition.models.CaloricExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaloricExpenditureRepository extends JpaRepository<CaloricExpenditure, Long> {
    public CaloricExpenditure findAllByPersonId(Long id);
}
