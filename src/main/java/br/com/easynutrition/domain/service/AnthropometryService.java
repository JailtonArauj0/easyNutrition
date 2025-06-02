package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.Anthropometry.AnthropometryRegisterDTO;
import br.com.easynutrition.api.dto.request.Anthropometry.AnthropometryUpdateDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.AnthropometryDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.BmiClassificationDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.BodyFatClassificationDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.WhrClassificationDTO;
import br.com.easynutrition.domain.enums.Gender;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.api.exception.EntityNotFoundException;
import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import br.com.easynutrition.domain.model.Anthropometry.NutritionalAssessment;
import br.com.easynutrition.domain.model.Person.Person;
import br.com.easynutrition.domain.repository.AnthropometryRepository;
import br.com.easynutrition.domain.repository.PersonRepository;
import br.com.easynutrition.utils.Classifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnthropometryService {
    private final AnthropometryRepository anthropometryRepository;
    private final PersonRepository personRepository;
    private static final String BMI_CLASSIFICATION = "bmi";
    private static final String WHR_CLASSIFICATION = "whr";
    private static final String BODY_FAT_CLASSIFICATION = "bodyFat";

    public AnthropometryService(AnthropometryRepository anthropometryRepository, PersonRepository personRepository) {
        this.anthropometryRepository = anthropometryRepository;
        this.personRepository = personRepository;
    }

    public List<AnthropometryDTO> findAllByPersonId(Long id) {
        List<Anthropometry> anthropometries = anthropometryRepository.findAllByPersonId(id);
        validateListSize(anthropometries);

        return anthropometries
                .stream()
                .map(AnthropometryDTO::new)
                .toList();
    }

    public AnthropometryDTO findById(Long id) {
        Anthropometry anthropometry = getAnthropometryIfExists(id);
        return anthropometry.toDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public AnthropometryDTO save(AnthropometryRegisterDTO anthropometryRegisterDTO) {
        Person person = getPersonIfExists(anthropometryRegisterDTO.getPerson().getId());

        Anthropometry anthropometry = anthropometryRegisterDTO.toEntity();
        anthropometry.setPerson(person);

        return anthropometryRepository.save(anthropometry).toDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public AnthropometryDTO update(AnthropometryUpdateDTO anthropometryUpdateDTO, Long anthropometryId) {
        Anthropometry anthropometry = getAnthropometryIfExists(anthropometryId);

        if (!anthropometry.getPerson().getId().equals(anthropometryUpdateDTO.getPerson().getId())) {
            throw new CustomException("Esta antropometria não pertence a pessoa informada.");
        }

        Optional.ofNullable(anthropometryUpdateDTO.getWeight()).ifPresent(anthropometry::setWeight);
        Optional.ofNullable(anthropometryUpdateDTO.getHeight()).ifPresent(anthropometry::setHeight);
        Optional.ofNullable(anthropometryUpdateDTO.getSex()).ifPresent(sex -> {
            anthropometry.setSex(Gender.valueOf(sex));
        });
        Optional.ofNullable(anthropometryUpdateDTO.getSkinfolds()).ifPresent(anthropometry::setSkinfolds);
        Optional.ofNullable(anthropometryUpdateDTO.getBodyCircunferences()).ifPresent(anthropometry::setBodyCircunferences);
        Optional.ofNullable(anthropometryUpdateDTO.getBodyCircunferences()).ifPresent(anthropometry::setBodyCircunferences);
        Optional.ofNullable(anthropometryUpdateDTO.getNutritionalAssessment()).ifPresent(anthropometry::setNutritionalAssessment);

        return anthropometryRepository.save(anthropometry).toDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        getAnthropometryIfExists(id);
        anthropometryRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public BmiClassificationDTO generateBmiClassificationIfNotExist(Long id) {
        Anthropometry anthropometry = getAnthropometryIfExists(id);

        if (verifyBmiClassificationExist(anthropometry)) {
            return new BmiClassificationDTO(anthropometry);
        }

        BmiClassificationDTO bmiClassificationDTO = (BmiClassificationDTO) generateClassification(anthropometry, BMI_CLASSIFICATION);

        NutritionalAssessment nutritionalAssessment = new NutritionalAssessment();
        nutritionalAssessment.setBmi(bmiClassificationDTO.getBmi());
        nutritionalAssessment.setBmiClassification(bmiClassificationDTO.getBmiClassification());

        // MANTÉM OS VALORES EXISTENTES PARA AS OUTRAS CLASSIFICAÇÕES
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getWhr).ifPresent(nutritionalAssessment::setWhr);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getWhrClassification).ifPresent(nutritionalAssessment::setWhrClassification);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBodyFat).ifPresent(nutritionalAssessment::setBodyFat);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBfClassification).ifPresent(nutritionalAssessment::setBfClassification);

        anthropometry.setNutritionalAssessment(nutritionalAssessment);
        anthropometryRepository.save(anthropometry);

        return bmiClassificationDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public WhrClassificationDTO generateWhrClassificationIfNotExist(Long id) {
        Anthropometry anthropometry = getAnthropometryIfExists(id);

        if (verifyWhrClassificationExist(anthropometry)) {
            return new WhrClassificationDTO(anthropometry);
        }

        WhrClassificationDTO whrClassificationDTO = (WhrClassificationDTO) generateClassification(anthropometry, WHR_CLASSIFICATION);

        NutritionalAssessment nutritionalAssessment = new NutritionalAssessment();
        nutritionalAssessment.setWhr(whrClassificationDTO.getWhr());
        nutritionalAssessment.setWhrClassification(whrClassificationDTO.getWhrClassification());

        // MANTÉM OS VALORES EXISTENTES PARA AS OUTRAS CLASSIFICAÇÕES
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBmi).ifPresent(nutritionalAssessment::setBmi);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBmiClassification).ifPresent(nutritionalAssessment::setBmiClassification);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBodyFat).ifPresent(nutritionalAssessment::setBodyFat);
        Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBfClassification).ifPresent(nutritionalAssessment::setBfClassification);

        anthropometry.setNutritionalAssessment(nutritionalAssessment);
        anthropometryRepository.save(anthropometry);

        return whrClassificationDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public BodyFatClassificationDTO generateBfClassificationIfNotExist(Long id) {
        Anthropometry anthropometry = getAnthropometryIfExists(id);

        if (verifySkinFoldsExist(anthropometry)) {
            if (!verifyBfClassificationExist(anthropometry)) {
                BodyFatClassificationDTO bodyFatClassificationDTO = (BodyFatClassificationDTO) generateClassification(anthropometry, BODY_FAT_CLASSIFICATION);

                NutritionalAssessment nutritionalAssessment = new NutritionalAssessment();
                nutritionalAssessment.setBodyFat(bodyFatClassificationDTO.getBodyFat());
                nutritionalAssessment.setBfClassification(bodyFatClassificationDTO.getBfClassification());

                // MANTÉM OS VALORES EXISTENTES PARA AS OUTRAS CLASSIFICAÇÕES
                Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBmi).ifPresent(nutritionalAssessment::setBmi);
                Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getBmiClassification).ifPresent(nutritionalAssessment::setBmiClassification);
                Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getWhr).ifPresent(nutritionalAssessment::setWhr);
                Optional.ofNullable(anthropometry.getNutritionalAssessment()).map(NutritionalAssessment::getWhrClassification).ifPresent(nutritionalAssessment::setWhrClassification);

                anthropometry.setNutritionalAssessment(nutritionalAssessment);
                anthropometryRepository.save(anthropometry);

                return bodyFatClassificationDTO;
            }

            return new BodyFatClassificationDTO(anthropometry);
        }

        throw new CustomException("Classificação de gordura corporal não pôde ser gerada, pois as dobras cutâneas não foram informadas.");
    }

    private void validateListSize(List<Anthropometry> anthropometries) {
        if (anthropometries.isEmpty()) {
            throw new EntityNotFoundException("Não foram encontradas avaliações.");
        }
    }

    private Person getPersonIfExists(Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado."));
    }

    private Anthropometry getAnthropometryIfExists(Long id) {
        return anthropometryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada."));
    }

    private boolean verifyBmiClassificationExist(Anthropometry anthropometry) {
        return Optional.ofNullable(anthropometry.getNutritionalAssessment())
                .map(assessment ->
                        assessment.getBmi() != null && assessment.getBmiClassification() != null
                )
                .orElse(false);
    }

    private boolean verifyWhrClassificationExist(Anthropometry anthropometry) {
        return Optional.ofNullable(anthropometry.getNutritionalAssessment())
                .map(assessment ->
                        assessment.getWhr() != null && assessment.getWhrClassification() != null
                )
                .orElse(false);
    }

    private boolean verifyBfClassificationExist(Anthropometry anthropometry) {
        return Optional.ofNullable(anthropometry.getNutritionalAssessment())
                .map(assessment ->
                        assessment.getBodyFat() != null && assessment.getBfClassification() != null
                ).orElse(false);
    }

    private boolean verifySkinFoldsExist(Anthropometry anthropometry) {
        return Optional.ofNullable(anthropometry.getSkinfolds())
                .map(assessment ->
                        assessment.getAbdominal() != null &&
                                assessment.getThigh() != null &&
                                assessment.getSubscapular() != null &&
                                assessment.getSuprailliac() != null &&
                                assessment.getTricipital() != null &&
                                assessment.getThoracic() != null &&
                                assessment.getMiddleAxillary() != null
                )
                .orElse(false);
    }

    private Object generateClassification(Anthropometry anthropometry, String classificationType) {
        Classifications classifications = new Classifications(anthropometry);

        switch (classificationType) {
            case "bmi" -> {
                return new BmiClassificationDTO(classifications.bmi(), classifications.bmiClassification());
            }
            case "whr" -> {
                return new WhrClassificationDTO(classifications.whr(), classifications.whrClassification());
            }
            case "bodyFat" -> {
                double bfPercentage = classifications.fatPercentagePollock();
                return new BodyFatClassificationDTO(bfPercentage, classifications.bfClassification(bfPercentage));
            }
        }

        throw new CustomException("Tipo de classificação inválido: " + classificationType);
    }
}
