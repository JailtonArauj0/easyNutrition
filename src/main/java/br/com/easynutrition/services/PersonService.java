package br.com.easynutrition.services;

import br.com.easynutrition.models.Person;
import br.com.easynutrition.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    @Transactional
    public Person save(Person person){
        Optional<Person> exists = personRepository.findPersonByCpf(person.getCpf());
        if (exists.isPresent()) {
            throw new RuntimeException("Este CPF já está sendo utilizado.");
        }
        LocalDate date = person.getBirthDate();
        int age = LocalDate.now().getYear() - date.getYear();
        person.setAge(age);

        return personRepository.save(person);
    }

    @Transactional
    public Person update(Person person) {
        this.findById(person.getId());
        return personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        this.findById(id);
        personRepository.deleteById(id);
    }
}
