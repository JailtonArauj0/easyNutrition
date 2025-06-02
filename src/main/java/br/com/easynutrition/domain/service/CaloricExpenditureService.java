package br.com.easynutrition.domain.service;

import br.com.easynutrition.domain.enums.Formula;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.api.exception.EntityNotFoundException;
import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import br.com.easynutrition.domain.repository.CaloricExpenditureRepository;
import br.com.easynutrition.utils.Equations;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CaloricExpenditureService {
    private final CaloricExpenditureRepository caloricExpenditureRepository;

    public CaloricExpenditureService(CaloricExpenditureRepository caloricExpenditureRepository) {
        this.caloricExpenditureRepository = caloricExpenditureRepository;
    }

    public CaloricExpenditure findByPersonId(Long id) {
        CaloricExpenditure caloricExpenditure = caloricExpenditureRepository.findAllByPersonId(id);
        if (caloricExpenditure == null) {
            throw new EntityNotFoundException("Cálculo calórico não encontrado para este paciente.");
        }
        return caloricExpenditure;
    }

    @Transactional
    public CaloricExpenditure save(CaloricExpenditure caloricExpenditure) {
        CaloricExpenditure exists = this.findByPersonId(caloricExpenditure.getPerson().getId());
        if (exists != null) {
            throw new CustomException("Já existe cálculo energético para este paciente, atualize-o.");
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

    @Transactional
    public CaloricExpenditure update(CaloricExpenditure caloricExpenditure) {
        if (caloricExpenditure.getId() == 0 || caloricExpenditure.getPerson().getId() == 0) {
            throw new CustomException("Informe os ID's corretamente!");
        }
        Optional<CaloricExpenditure> exists = caloricExpenditureRepository.findById(caloricExpenditure.getId());
        if (exists.isEmpty()) {
            throw new EntityNotFoundException("Não existe cálculo energético para este paciente.");
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

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        CaloricExpenditure caloricExpenditure = getCaloricExpenditureIfExist(id);
        caloricExpenditureRepository.delete(caloricExpenditure);
    }

    private CaloricExpenditure getCaloricExpenditureIfExist(Long id){
        return caloricExpenditureRepository.findById(id).orElseThrow(() -> new  EntityNotFoundException("Cálculo energético não encontrado."));
    }
}
