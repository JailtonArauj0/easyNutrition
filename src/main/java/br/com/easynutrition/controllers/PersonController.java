package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.PersonDTO;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    private List<PersonDTO> findAll() {
        List<Person> personList = personService.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList){
            PersonDTO personDTO = new PersonDTO();
            BeanUtils.copyProperties(person, personDTO);
            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    @GetMapping("/{id}")
    private ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        Person person = personService.findById(id);

        PersonDTO personDTO = new PersonDTO(person);
        if (person != null) {
            return new ResponseEntity<>(personDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<PersonDTO> save(@RequestBody @Valid PersonDTO personDTO){
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        Person savedPerson = personService.save(person);
        PersonDTO savedPersonDTO = new PersonDTO();
        BeanUtils.copyProperties(savedPerson, savedPersonDTO);
        return new ResponseEntity<>(savedPersonDTO, HttpStatus.CREATED);
    }


    @PutMapping
    private ResponseEntity<Person> update(@RequestBody Person person) {
        Person savedPerson = personService.update(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
