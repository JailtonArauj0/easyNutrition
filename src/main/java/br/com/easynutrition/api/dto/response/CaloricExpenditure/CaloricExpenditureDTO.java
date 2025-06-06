package br.com.easynutrition.api.dto.response.CaloricExpenditure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CaloricExpenditureDTO {
    private Double activityFactor;
    private Double geb;
    private Double get;
}
