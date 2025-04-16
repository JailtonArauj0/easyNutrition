package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.PersonDTO;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    private List<PersonDTO> findAll() {
        List<Person> personList = personService.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList) {
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
        BeanUtils.copyProperties(person, personDTO);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<PersonDTO> save(@RequestBody @Valid PersonDTO personDTO) {
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        Person savedPerson = personService.save(person);
        BeanUtils.copyProperties(savedPerson, personDTO);
        return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<PersonDTO> update(@RequestBody @Valid PersonDTO personDTO) {
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        Person savedPerson = personService.update(person);
        BeanUtils.copyProperties(savedPerson, personDTO);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
