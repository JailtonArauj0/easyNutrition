package br.com.easynutrition.domain.service;

import br.com.easynutrition.api.dto.request.Person.PersonRegisterDTO;
import br.com.easynutrition.api.dto.response.Person.PersonDTO;
import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.api.exception.EntityNotFoundException;
import br.com.easynutrition.domain.model.Person.Person;
import br.com.easynutrition.domain.model.User.Users;
import br.com.easynutrition.domain.repository.PersonRepository;
import br.com.easynutrition.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll()
                .stream()
                .map(PersonDTO::new)
                .toList();
    }

    public PersonDTO findById(Long id) {
        Person person = personExistAndGet(id);
        return new PersonDTO(person);
    }

    @Transactional(rollbackFor = Exception.class)
    public PersonDTO save(PersonRegisterDTO personRegisterDTO) {
        validateUniqueCpf(personRegisterDTO.getCpf());
        Users nutritionist = nutritionistExistAndGet(personRegisterDTO.getNutritionistId());

        Person person = personRegisterDTO.toEntity(nutritionist);

        Person personSaved = personRepository.save(person);

        return new PersonDTO(personSaved);
    }

    @Transactional(rollbackFor = Exception.class)
    public PersonDTO update(PersonRegisterDTO personRegisterDTO, Long id) {
        Person person = personExistAndGet(id);

        if (!personRegisterDTO.getCpf().equals(person.getCpf())) {
            validateUniqueCpf(personRegisterDTO.getCpf());
        }

        Users nutritionist = nutritionistExistAndGet(personRegisterDTO.getNutritionistId());

        person.setFirstName(personRegisterDTO.getFirstName());
        person.setLastName(personRegisterDTO.getLastName());
        person.setPhone(personRegisterDTO.getPhone());
        person.setCpf(personRegisterDTO.getCpf());
        person.setBirthDate(personRegisterDTO.getBirthDate());
        person.setNutritionist(nutritionist);

        int age = LocalDate.now().getYear() - personRegisterDTO.getBirthDate().getYear();
        person.setAge(age);

        Person personSaved = personRepository.save(person);

        return new PersonDTO(personSaved);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        personExistAndGet(id);
        personRepository.deleteById(id);
    }

    public Person personExistAndGet(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private void validateUniqueCpf(String cpf) {
        personRepository.findPersonByCpf(cpf).ifPresent(
                person -> {
                    throw new CustomException("Já existe um usuário cadastrado com o CPF informado.");
                }
        );
    }

    private Users nutritionistExistAndGet(Long nutritionistId) {
        return userRepository.findById(nutritionistId)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado com o ID informado."));
    }
}
