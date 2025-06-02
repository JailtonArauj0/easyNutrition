package br.com.easynutrition.api.controller;

import br.com.easynutrition.api.dto.request.Person.PersonRegisterDTO;
import br.com.easynutrition.api.dto.response.Person.PersonDTO;
import br.com.easynutrition.domain.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    private ResponseEntity<List<PersonDTO>> findAll() {
        List<PersonDTO> personList = personService.findAll();
        return ResponseEntity.ok(personList);
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
