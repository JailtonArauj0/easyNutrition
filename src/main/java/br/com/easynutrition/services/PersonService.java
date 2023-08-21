package br.com.easynutrition.services;

import br.com.easynutrition.models.Person;
import br.com.easynutrition.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return personRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não existe."));
    }
    public Person save(Person person){
        Optional<Person> exists = personRepository.findPersonByCpf(person.getCpf());
        if (exists.isPresent()) {
            throw new RuntimeException("CPF já está sendo utilizado.");
        }
        return personRepository.save(person);
    }
}
