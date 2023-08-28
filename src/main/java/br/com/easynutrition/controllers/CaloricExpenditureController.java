package br.com.easynutrition.controllers;

import br.com.easynutrition.dtos.CaloricExpenditureDTO;
import br.com.easynutrition.models.CaloricExpenditure;
import br.com.easynutrition.services.CaloricExpenditureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/caloric-expenditure")
public class CaloricExpenditureController {
    @Autowired
    private CaloricExpenditureService caloricExpenditureService;

    @GetMapping
    private ResponseEntity<CaloricExpenditureDTO> findByPersonId(@RequestBody CaloricExpenditure caloricExpenditure) {
        CaloricExpenditureDTO expenditureDTO = new CaloricExpenditureDTO();
        CaloricExpenditure expenditure = caloricExpenditureService.findByPersonId(caloricExpenditure.getPerson().getId());
        BeanUtils.copyProperties(expenditure, expenditureDTO);
        return new ResponseEntity<>(expenditureDTO, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<CaloricExpenditure> save(@RequestBody CaloricExpenditure caloricExpenditure) {
        var saved = caloricExpenditureService.save(caloricExpenditure);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
