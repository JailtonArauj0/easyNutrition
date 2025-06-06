package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.CaloricExpenditure.CaloricExpenditureRegisterDTO;
import br.com.easynutrition.api.dto.response.CaloricExpenditure.CaloricExpenditureDTO;
import br.com.easynutrition.domain.enums.Formula;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.api.exception.EntityNotFoundException;
import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import br.com.easynutrition.domain.repository.AnthropometryRepository;
import br.com.easynutrition.domain.repository.CaloricExpenditureRepository;
import br.com.easynutrition.utils.Equations;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CaloricExpenditureService {
    private final CaloricExpenditureRepository caloricExpenditureRepository;
    private final AnthropometryRepository anthropometryRepository;

    public CaloricExpenditureService(CaloricExpenditureRepository caloricExpenditureRepository, AnthropometryRepository anthropometryRepository) {
        this.caloricExpenditureRepository = caloricExpenditureRepository;
        this.anthropometryRepository = anthropometryRepository;
    }

    public CaloricExpenditure findByPersonId(Long id) {
        CaloricExpenditure caloricExpenditure = caloricExpenditureRepository.findAllByPersonId(id);
        if (caloricExpenditure == null) {
            throw new EntityNotFoundException("Cálculo calórico não encontrado para este paciente.");
        }
        return caloricExpenditure;
    }

    @Transactional(rollbackFor = Exception.class)
    public CaloricExpenditureDTO calculateEer(CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        Anthropometry anthropometry = getAnthropometryIfExists(caloricExpenditureRegisterDTO.getAnthropometryId());

        validateActivityFactor(anthropometry.getSex().name(), caloricExpenditureRegisterDTO.getActivityFactor());

        CaloricExpenditure caloricExpenditure = generateCaloricExpenditure(anthropometry, caloricExpenditureRegisterDTO.getActivityFactor(), Formula.EER_IOM);
        return caloricExpenditureRepository.save(caloricExpenditure).toDTO();
    }

    private CaloricExpenditure generateCaloricExpenditure(Anthropometry anthropometry, Double activityFactor, Formula formula) {
        Equations equations = new Equations(anthropometry);
        equations.setActivityFactor(activityFactor);

        return switch (formula) {
            case HARRIS_BENEDICT -> equations.harrisBenedict(anthropometry.getSex().name());
            case EER_IOM -> equations.eerIom(anthropometry.getSex().name());
            case MIFFLIN_ST_JEOR -> equations.mifflin(anthropometry.getSex().name());
        };
    }

//    @Transactional(rollbackFor = Exception.class)
//    public CaloricExpenditure save(CaloricExpenditure caloricExpenditure) {
//        Equations equations = new Equations();
//        BeanUtils.copyProperties(caloricExpenditure, equations);
//
//        if (caloricExpenditure.getFormula().equals(Formula.HARRIS_BENEDICT)) {
//            equations.harrisBenedict(caloricExpenditure.getSex());
//            caloricExpenditure.setGeb(equations.getGeb());
//            caloricExpenditure.setGet(equations.getGet());
//
//        } else if (caloricExpenditure.getFormula().equals(Formula.MIFFLIN_ST_JEOR)) {
//            equations.mifflin(caloricExpenditure.getSex());
//            caloricExpenditure.setGeb(equations.getGeb());
//            caloricExpenditure.setGet(equations.getGet());
//
//        } else if (caloricExpenditure.getFormula().equals(Formula.EER_IOM)) {
//            equations.eerIom(caloricExpenditure.getSex());
//            caloricExpenditure.setGeb(0);
//            caloricExpenditure.setGet(equations.getGet());
//        }
//        return caloricExpenditureRepository.save(caloricExpenditure);
//    }

//    @Transactional
//    public CaloricExpenditure update(CaloricExpenditure caloricExpenditure) {
//        if (caloricExpenditure.getId() == 0 || caloricExpenditure.getPerson().getId() == 0) {
//            throw new CustomException("Informe os ID's corretamente!");
//        }
//        Optional<CaloricExpenditure> exists = caloricExpenditureRepository.findById(caloricExpenditure.getId());
//        if (exists.isEmpty()) {
//            throw new EntityNotFoundException("Não existe cálculo energético para este paciente.");
//        }
//
//        Equations equations = new Equations();
//        BeanUtils.copyProperties(caloricExpenditure, equations);
//
//        if (caloricExpenditure.getFormula().equals(Formula.HARRIS_BENEDICT)) {
//            equations.harrisBenedict(caloricExpenditure.getSex());
//            caloricExpenditure.setGeb(equations.getGeb());
//            caloricExpenditure.setGet(equations.getGet());
//
//        } else if (caloricExpenditure.getFormula().equals(Formula.MIFFLIN_ST_JEOR)) {
//            equations.mifflin(caloricExpenditure.getSex());
//            caloricExpenditure.setGeb(equations.getGeb());
//            caloricExpenditure.setGet(equations.getGet());
//
//        } else if (caloricExpenditure.getFormula().equals(Formula.EER_IOM)) {
//            equations.eerIom(caloricExpenditure.getSex());
////            caloricExpenditure.setGeb(0);
//            caloricExpenditure.setGet(equations.getGet());
//        }
//        return caloricExpenditureRepository.save(caloricExpenditure);
//    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        CaloricExpenditure caloricExpenditure = getCaloricExpenditureIfExist(id);
        caloricExpenditureRepository.delete(caloricExpenditure);
    }

    private CaloricExpenditure getCaloricExpenditureIfExist(Long id){
        return caloricExpenditureRepository.findById(id).orElseThrow(() -> new  EntityNotFoundException("Cálculo energético não encontrado."));
    }

    private Anthropometry getAnthropometryIfExists(Long id) {
        return anthropometryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada."));
    }

    private void validateActivityFactor(String sex, Double activityFactor) {
        List<Double> allowedAfMan = List.of(1.00, 1.11, 1.25, 1.48);
        List<Double> allowAfWomen = List.of(1.00, 1.12, 1.27, 1.45);

        if(sex.equalsIgnoreCase("Masculino")) {
            if(!allowedAfMan.contains(activityFactor)){
                throw new CustomException("Fator de atividade incorreto, valores permitidos: " + allowedAfMan);
            }
        } else {
            if(!allowAfWomen.contains(activityFactor)){
                throw new CustomException("Fator de atividade incorreto, valores permitidos: " + allowAfWomen);
            }
        }
    }
}
