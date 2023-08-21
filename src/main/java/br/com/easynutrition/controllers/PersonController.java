package br.com.easynutrition.controllers;

import br.com.easynutrition.models.Person;
import br.com.easynutrition.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/users")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    private ResponseEntity<Person> save(@RequestBody Person person) {
        Person savedPerson = personService.save(person);
        return ResponseEntity.ok(savedPerson);
    }

}
