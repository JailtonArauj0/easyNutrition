package br.com.easynutrition.services;

import br.com.easynutrition.dtos.request.AnthropometryRegisterDTO;
import br.com.easynutrition.dtos.response.AnthropometryDTO;
import br.com.easynutrition.dtos.response.BmiClassificationDTO;
import br.com.easynutrition.enums.Gender;
import br.com.easynutrition.exception.CustomException;
import br.com.easynutrition.exception.EntityNotFoundException;
import br.com.easynutrition.models.Anthropometry.Anthropometry;
import br.com.easynutrition.models.Anthropometry.NutritionalAssessment;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.repositories.AnthropometryRepository;
import br.com.easynutrition.repositories.PersonRepository;
import br.com.easynutrition.utils.Classifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnthropometryService {
    private final AnthropometryRepository anthropometryRepository;
    private final PersonRepository personRepository;

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
    public AnthropometryDTO update(AnthropometryRegisterDTO anthropometryRegisterDTO, Long anthropometryId) {
        Anthropometry anthropometry = getAnthropometryIfExists(anthropometryId);

        if (!anthropometry.getPerson().getId().equals(anthropometryRegisterDTO.getPerson().getId())) {
            throw new CustomException("Esta antropometria não pertence a pessoa informada.");
        }
        ;

        anthropometry.setWeight(anthropometryRegisterDTO.getWeight());
        anthropometry.setHeight(anthropometryRegisterDTO.getHeight());
        anthropometry.setSex(Gender.valueOf(anthropometryRegisterDTO.getSex()));
        anthropometry.setSkinfolds(anthropometryRegisterDTO.getSkinfolds());
        anthropometry.setBodyCircunferences(anthropometryRegisterDTO.getBodyCircunferences());
        anthropometry.setNutritionalAssessment(anthropometryRegisterDTO.getNutritionalAssessment());

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

        BmiClassificationDTO bmiClassificationDTO = generateBmiClassification(anthropometry);

        NutritionalAssessment nutritionalAssessment = new NutritionalAssessment();
        nutritionalAssessment.setBmi(bmiClassificationDTO.getBmi());
        nutritionalAssessment.setBmiClassification(bmiClassificationDTO.getBmiClassification());

        anthropometry.setNutritionalAssessment(nutritionalAssessment);
        anthropometryRepository.save(anthropometry);

        return bmiClassificationDTO;
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

    private BmiClassificationDTO generateBmiClassification(Anthropometry anthropometry) {
        Classifications classifications = new Classifications(anthropometry);

        return new BmiClassificationDTO(classifications.bmi(), classifications.bmiClassification());
    }
}
