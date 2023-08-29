package br.com.easynutrition.services;

import br.com.easynutrition.models.CaloricExpenditure;
import br.com.easynutrition.models.Formula;
import br.com.easynutrition.repositories.CaloricExpenditureRepository;
import br.com.easynutrition.utils.Equations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaloricExpenditureService {
    @Autowired
    private CaloricExpenditureRepository caloricExpenditureRepository;

    public CaloricExpenditure findByPersonId(Long id) {
        return caloricExpenditureRepository.findAllByPersonId(id);
    }

    public CaloricExpenditure save(CaloricExpenditure caloricExpenditure) {
        CaloricExpenditure exists = this.findByPersonId(caloricExpenditure.getPerson().getId());
        if (exists != null) {
            throw new RuntimeException("Já existe cálculo energético para este paciente.");
        }
        Equations equations = new Equations();
        BeanUtils.copyProperties(caloricExpenditure, equations);

        if (caloricExpenditure.getFormula().equals(Formula.HARRIS_BENEDICT)) {
            equations.harrisBenedict(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(equations.getGeb());
            caloricExpenditure.setGet(equations.getGet());

        } else if (caloricExpenditure.getFormula().equals(Formula.MIFFLIN_ST_JEOR)) {
            equations.mifflin(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(equations.getGeb());
            caloricExpenditure.setGet(equations.getGet());

        } else if (caloricExpenditure.getFormula().equals(Formula.EER_IOM)) {
            equations.eerIom(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(0);
            caloricExpenditure.setGet(equations.getGet());
        }
        return caloricExpenditureRepository.save(caloricExpenditure);
    }

    public CaloricExpenditure update(CaloricExpenditure caloricExpenditure) {
        Optional<CaloricExpenditure> exists = caloricExpenditureRepository.findById(caloricExpenditure.getId());
        if (exists.isEmpty()) {
            throw new RuntimeException("Não existe cálculo energético para este paciente.");
        }

        Equations equations = new Equations();
        BeanUtils.copyProperties(caloricExpenditure, equations);

        if (caloricExpenditure.getFormula().equals(Formula.HARRIS_BENEDICT)) {
            equations.harrisBenedict(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(equations.getGeb());
            caloricExpenditure.setGet(equations.getGet());

        } else if (caloricExpenditure.getFormula().equals(Formula.MIFFLIN_ST_JEOR)) {
            equations.mifflin(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(equations.getGeb());
            caloricExpenditure.setGet(equations.getGet());

        } else if (caloricExpenditure.getFormula().equals(Formula.EER_IOM)) {
            equations.eerIom(caloricExpenditure.getSex());
            caloricExpenditure.setGeb(0);
            caloricExpenditure.setGet(equations.getGet());
        }
        return caloricExpenditureRepository.save(caloricExpenditure);
    }
}
