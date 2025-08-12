package br.com.easynutrition.api.dto.response.CaloricExpenditure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CaloricExpenditureDTO {
    private Long id;
    private String formula;
    private Double activityFactor;
    private Double geb;
    private Double get;
}
