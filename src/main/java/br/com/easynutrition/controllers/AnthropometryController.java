package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.AnthropometryDTO;
import br.com.easynutrition.models.Anthropometry;
import br.com.easynutrition.services.AnthropometryService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/anthropometry")
public class AnthropometryController {
    @Autowired
    private AnthropometryService anthropometryService;

    @GetMapping
    public ResponseEntity<List<AnthropometryDTO>> findAllByEvaluationDate(@RequestBody Anthropometry anthropometry) {
        List<AnthropometryDTO> anthropometryDTO = new ArrayList<>();
        List<Anthropometry> anthropometryList = anthropometryService.findAllByEvaluationDate(anthropometry.getEvaluationDate(), anthropometry.getPerson().getId());
        for (var anthropo: anthropometryList) {
            AnthropometryDTO dto = new AnthropometryDTO();
            BeanUtils.copyProperties(anthropo, dto);
            anthropometryDTO.add(dto);
        }
        return new ResponseEntity<>(anthropometryDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AnthropometryDTO>> findAllByPersonId(@PathVariable Long id) {
        List<AnthropometryDTO> anthropometryDTO = new ArrayList<>();
        List<Anthropometry> anthropometryList = anthropometryService.findAllByPersonId(id);
        for (var anthropometry: anthropometryList) {
            AnthropometryDTO dto = new AnthropometryDTO();
            BeanUtils.copyProperties(anthropometry, dto);
            anthropometryDTO.add(dto);
        }
        return new ResponseEntity<>(anthropometryDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AnthropometryDTO> save(@RequestBody @Valid AnthropometryDTO anthropometryDTO) {
        Anthropometry anthropometry = new Anthropometry();
        BeanUtils.copyProperties(anthropometryDTO, anthropometry);
        Anthropometry anthropometrySaved = anthropometryService.save(anthropometry);
        BeanUtils.copyProperties(anthropometrySaved, anthropometryDTO);
        return new ResponseEntity<>(anthropometryDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AnthropometryDTO> update(@RequestBody @Valid AnthropometryDTO anthropometryDTO) {
        Anthropometry anthropometry = new Anthropometry();
        BeanUtils.copyProperties(anthropometryDTO, anthropometry);
        Anthropometry anthropometryUpdated = anthropometryService.update(anthropometry);
        BeanUtils.copyProperties(anthropometryUpdated, anthropometryDTO);
        return new ResponseEntity<>(anthropometryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        anthropometryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
