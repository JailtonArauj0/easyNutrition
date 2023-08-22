package br.com.easynutrition.controllers;

import br.com.easynutrition.models.Person;
import br.com.easynutrition.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<Person> save(@RequestBody Person person){
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
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
