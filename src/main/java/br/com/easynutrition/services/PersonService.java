package br.com.easynutrition.services;

import br.com.easynutrition.dtos.request.PersonRegisterDTO;
import br.com.easynutrition.dtos.response.PersonDTO;
import br.com.easynutrition.exception.CustomException;
import br.com.easynutrition.exception.EntityNotFoundException;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.models.Users;
import br.com.easynutrition.repositories.PersonRepository;
import br.com.easynutrition.repositories.UserRepository;
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

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
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

    @Transactional(rollbackFor = Exception.class)
    public PersonDTO save(PersonRegisterDTO personRegisterDTO) {
        validateUniqueCpf(personRegisterDTO.getCpf());
        Users nutritionist = nutritionistExistAndGet(personRegisterDTO.getNutritionistId());

        Person person = personRegisterDTO.toEntity(nutritionist);

        Person personSaved = personRepository.save(person);

        return new PersonDTO(personSaved);
    }

    @Transactional
    public Person update(Person person) {
        this.findById(person.getId());
        LocalDate date = person.getBirthDate();
        int age = LocalDate.now().getYear() - date.getYear();
        person.setAge(age);
        return personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        this.findById(id);
        personRepository.deleteById(id);
    }
}
