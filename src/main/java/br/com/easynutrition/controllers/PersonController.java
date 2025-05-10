package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.request.PersonRegisterDTO;
import br.com.easynutrition.dtos.response.PersonDTO;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    private List<PersonRegisterDTO> findAll() {
        List<Person> personList = personService.findAll();
        List<PersonRegisterDTO> personRegisterDTOList = new ArrayList<>();
        for (Person person : personList) {
            PersonRegisterDTO personRegisterDTO = new PersonRegisterDTO();
            BeanUtils.copyProperties(person, personRegisterDTO);
            personRegisterDTOList.add(personRegisterDTO);
        }
        return personRegisterDTOList;
    }

    @GetMapping("/{id}")
    private ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        PersonDTO person = personService.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<PersonDTO> save(@RequestBody @Valid PersonRegisterDTO personRegisterDTO) {
        PersonDTO savedPerson = personService.save(personRegisterDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedPerson);
    }

    @PutMapping("/{id}")
    private ResponseEntity<PersonDTO> update(@RequestBody @Valid PersonRegisterDTO personRegisterDTO, @PathVariable Long id) {
        PersonDTO savedPerson = personService.update(personRegisterDTO, id);
        return ResponseEntity.ok(savedPerson);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
