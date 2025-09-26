package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.CaloricExpenditure.CaloricExpenditureRegisterDTO;
import br.com.easynutrition.api.dto.response.CaloricExpenditure.CaloricExpenditureDTO;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.api.exception.EntityNotFoundException;
import br.com.easynutrition.domain.enums.Formula;
import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import br.com.easynutrition.domain.repository.AnthropometryRepository;
import br.com.easynutrition.domain.repository.CaloricExpenditureRepository;
import br.com.easynutrition.utils.Equations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaloricExpenditureService {
    private final CaloricExpenditureRepository caloricExpenditureRepository;
    private final AnthropometryRepository anthropometryRepository;

    public CaloricExpenditureService(CaloricExpenditureRepository caloricExpenditureRepository, AnthropometryRepository anthropometryRepository) {
        this.caloricExpenditureRepository = caloricExpenditureRepository;
        this.anthropometryRepository = anthropometryRepository;
    }

    public CaloricExpenditureDTO findById(Long id) {
        return caloricExpenditureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cálculo calórico não encontrado.")).toDTO();
    }

    public List<CaloricExpenditureDTO> findByPersonId(Long id) {
        List<CaloricExpenditure> listOfEstimatives = caloricExpenditureRepository.findAllByPersonId(id);

        if (listOfEstimatives.isEmpty()) {
            throw new EntityNotFoundException("Nenhum cálculo calórico não encontrado para este paciente.");
        }

        return listOfEstimatives.stream().map(CaloricExpenditure::toDTO).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public CaloricExpenditureDTO calculateEer(CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        Anthropometry anthropometry = getAnthropometryIfExists(caloricExpenditureRegisterDTO.getAnthropometryId());

        validateEerActivityFactor(anthropometry.getSex().name(), caloricExpenditureRegisterDTO.getActivityFactor());

        CaloricExpenditure caloricExpenditure = generateCaloricExpenditure(anthropometry, caloricExpenditureRegisterDTO.getActivityFactor(), Formula.EER_IOM);
        return caloricExpenditureRepository.save(caloricExpenditure).toDTO();
    }

    public CaloricExpenditureDTO calculateHarrisBenedict(CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        Anthropometry anthropometry = getAnthropometryIfExists(caloricExpenditureRegisterDTO.getAnthropometryId());

        validateActivityFactor(caloricExpenditureRegisterDTO.getActivityFactor());

        CaloricExpenditure caloricExpenditure = generateCaloricExpenditure(anthropometry, caloricExpenditureRegisterDTO.getActivityFactor(), Formula.HARRIS_BENEDICT);

        return caloricExpenditureRepository.save(caloricExpenditure).toDTO();
    }

    public CaloricExpenditureDTO calculateMifflin(CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        Anthropometry anthropometry = getAnthropometryIfExists(caloricExpenditureRegisterDTO.getAnthropometryId());

        validateActivityFactor(caloricExpenditureRegisterDTO.getActivityFactor());

        CaloricExpenditure caloricExpenditure = generateCaloricExpenditure(anthropometry, caloricExpenditureRegisterDTO.getActivityFactor(), Formula.MIFFLIN_ST_JEOR);

        return caloricExpenditureRepository.save(caloricExpenditure).toDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        CaloricExpenditure caloricExpenditure = getCaloricExpenditureIfExist(id);
        caloricExpenditureRepository.delete(caloricExpenditure);
    }

    private CaloricExpenditure getCaloricExpenditureIfExist(Long id) {
        return caloricExpenditureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cálculo energético não encontrado."));
    }

    private Anthropometry getAnthropometryIfExists(Long id) {
        return anthropometryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada."));
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

    private void validateEerActivityFactor(String sex, Double activityFactor) {
        List<Double> allowedAfMan = List.of(1.00, 1.11, 1.25, 1.48);
        List<Double> allowAfWomen = List.of(1.00, 1.12, 1.27, 1.45);

        if (sex.equalsIgnoreCase("Masculino")) {
            if (!allowedAfMan.contains(activityFactor)) {
                throw new CustomException("Fator de atividade incorreto, valores permitidos: " + allowedAfMan);
            }
        } else {
            if (!allowAfWomen.contains(activityFactor)) {
                throw new CustomException("Fator de atividade incorreto, valores permitidos: " + allowAfWomen);
            }
        }
    }

    private void validateActivityFactor(Double activityFactor) {
        List<Double> allowedAfs = List.of(1.2, 1.375, 1.55, 1.725, 1.9);

        if (!allowedAfs.contains(activityFactor)) {
            throw new CustomException("Fator de atividade incorreto, valores permitidos: " + allowedAfs);
        }
    }
}
