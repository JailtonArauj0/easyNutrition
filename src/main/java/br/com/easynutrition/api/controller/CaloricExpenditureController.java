package br.com.easynutrition.api.controller;

import br.com.easynutrition.api.dto.request.CaloricExpenditure.CaloricExpenditureRegisterDTO;
import br.com.easynutrition.api.dto.response.CaloricExpenditure.CaloricExpenditureDTO;
import br.com.easynutrition.domain.service.CaloricExpenditureService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/caloricExpenditure")
public class CaloricExpenditureController {
    private final CaloricExpenditureService caloricExpenditureService;

    public CaloricExpenditureController(CaloricExpenditureService caloricExpenditureService) {
        this.caloricExpenditureService = caloricExpenditureService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaloricExpenditureDTO> findById(@PathVariable Long id) {
        CaloricExpenditureDTO caloricEstimate = caloricExpenditureService.findById(id);
        return ResponseEntity.ok(caloricEstimate);
    }

    @GetMapping("/findAll/{personId}")
    public ResponseEntity<List<CaloricExpenditureDTO>> findByPersonId(@PathVariable Long personId) {
        List<CaloricExpenditureDTO> caloricEstimates = caloricExpenditureService.findByPersonId(personId);
        return ResponseEntity.ok(caloricEstimates);
    }

    @PostMapping("/calculate/eer")
    public ResponseEntity<CaloricExpenditureDTO> calculateEer(@RequestBody CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        CaloricExpenditureDTO expenditureDTO = caloricExpenditureService.calculateEer(caloricExpenditureRegisterDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("caloricExpenditure/{id}")
                .buildAndExpand(expenditureDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(expenditureDTO);
    }

    @PostMapping("/calculate/harrisBenedict")
    public ResponseEntity<CaloricExpenditureDTO> calculateHarrisBenedict(@RequestBody CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        CaloricExpenditureDTO expenditureDTO = caloricExpenditureService.calculateHarrisBenedict(caloricExpenditureRegisterDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("caloricExpenditure/{id}")
                .buildAndExpand(expenditureDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(expenditureDTO);
    }

    @PostMapping("/calculate/mifflin")
    public ResponseEntity<CaloricExpenditureDTO> calculateMifflin(@RequestBody CaloricExpenditureRegisterDTO caloricExpenditureRegisterDTO) {
        CaloricExpenditureDTO expenditureDTO = caloricExpenditureService.calculateMifflin(caloricExpenditureRegisterDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("caloricExpenditure/{id}")
                .buildAndExpand(expenditureDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(expenditureDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        caloricExpenditureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
