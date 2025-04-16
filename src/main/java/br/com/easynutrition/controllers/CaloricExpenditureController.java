package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.CaloricExpenditureDTO;
import br.com.easynutrition.models.CaloricExpenditure;
import br.com.easynutrition.services.CaloricExpenditureService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/caloric-expenditure")
public class CaloricExpenditureController {
    private final CaloricExpenditureService caloricExpenditureService;

    public CaloricExpenditureController(CaloricExpenditureService caloricExpenditureService) {
        this.caloricExpenditureService = caloricExpenditureService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<CaloricExpenditureDTO> findByPersonId(@PathVariable Long id) {
        CaloricExpenditureDTO expenditureDTO = new CaloricExpenditureDTO();
        CaloricExpenditure expenditure = caloricExpenditureService.findByPersonId(id);
        BeanUtils.copyProperties(expenditure, expenditureDTO);
        return new ResponseEntity<>(expenditureDTO, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<CaloricExpenditureDTO> save(@RequestBody CaloricExpenditure caloricExpenditure) {
        var saved = caloricExpenditureService.save(caloricExpenditure);
        CaloricExpenditureDTO expenditureDTO = new CaloricExpenditureDTO();
        BeanUtils.copyProperties(saved, expenditureDTO);
        return new ResponseEntity<>(expenditureDTO, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<CaloricExpenditureDTO> update(@RequestBody CaloricExpenditure caloricExpenditure) {
        var updated = caloricExpenditureService.update(caloricExpenditure);
        CaloricExpenditureDTO expenditureDTO = new CaloricExpenditureDTO();
        BeanUtils.copyProperties(updated, expenditureDTO);
        return new ResponseEntity<>(expenditureDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        caloricExpenditureService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
