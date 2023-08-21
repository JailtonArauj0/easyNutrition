package br.com.easynutrition.controllers;

import br.com.easynutrition.models.Person;
import br.com.easynutrition.repositories.PersonRepository;
import br.com.easynutrition.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    private List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = personService.findById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        }
        return new ResponseEntity<>(person, HttpStatus.NO_CONTENT);
    }

    @PostMapping
    private ResponseEntity<Person> save(@RequestBody Person person){
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

}
