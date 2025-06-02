package br.com.easynutrition.api.dto;

import br.com.easynutrition.domain.enums.Formula;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaloricExpenditureDTO {
    private long id;
    private double weight;
    private double height;
    private int age;
    private char sex;
    private double activityFactor;
    private Formula formula;
    private double geb;
    private double get;
}
