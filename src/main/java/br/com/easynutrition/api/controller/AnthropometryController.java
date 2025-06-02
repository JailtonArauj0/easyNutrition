package br.com.easynutrition.api.controller;

import br.com.easynutrition.api.dto.request.Anthropometry.AnthropometryRegisterDTO;
import br.com.easynutrition.api.dto.request.Anthropometry.AnthropometryUpdateDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.AnthropometryDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.BmiClassificationDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.BodyFatClassificationDTO;
import br.com.easynutrition.api.dto.response.Anthropometry.WhrClassificationDTO;
import br.com.easynutrition.domain.service.AnthropometryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/anthropometry")
public class AnthropometryController {
    private final AnthropometryService anthropometryService;

    public AnthropometryController(AnthropometryService anthropometryService) {
        this.anthropometryService = anthropometryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnthropometryDTO> findById(@PathVariable Long id) {
        AnthropometryDTO anthropometry = anthropometryService.findById(id);
        return ResponseEntity.ok(anthropometry);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<List<AnthropometryDTO>> findAllByPersonId(@PathVariable Long id) {
        List<AnthropometryDTO> anthropometries = anthropometryService.findAllByPersonId(id);
        return ResponseEntity.ok(anthropometries);
    }

    @PostMapping
    public ResponseEntity<AnthropometryDTO> save(@RequestBody @Valid AnthropometryRegisterDTO anthropometryRegisterDTODTO) {
        AnthropometryDTO anthropometrySaved = anthropometryService.save(anthropometryRegisterDTODTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(anthropometrySaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(anthropometrySaved);
    }

    @PutMapping("/{anthropometryId}")
    public ResponseEntity<AnthropometryDTO> update(@RequestBody @Valid AnthropometryUpdateDTO anthropometryUpdateDTO,
                                                   @PathVariable Long anthropometryId) {
        AnthropometryDTO anthropometryUpdated = anthropometryService.update(anthropometryUpdateDTO, anthropometryId);
        return ResponseEntity.ok(anthropometryUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        anthropometryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bmiClassification")
    public ResponseEntity<BmiClassificationDTO> getBmiClassification(@PathVariable Long id) {
        BmiClassificationDTO bmiClassification = anthropometryService.generateBmiClassificationIfNotExist(id);
        return ResponseEntity.ok().body(bmiClassification);
    }

    @GetMapping("/{id}/whrClassification")
    public ResponseEntity<WhrClassificationDTO> getWhrClassification(@PathVariable Long id) {
        WhrClassificationDTO whrClassification = anthropometryService.generateWhrClassificationIfNotExist(id);
        return ResponseEntity.ok().body(whrClassification);
    }

    @GetMapping("/{id}/bodyFatClassification")
    public ResponseEntity<BodyFatClassificationDTO> getBodyFatClassification(@PathVariable Long id) {
        BodyFatClassificationDTO bodyFatClassification = anthropometryService.generateBfClassificationIfNotExist(id);
        return ResponseEntity.ok(bodyFatClassification);
    }

}
