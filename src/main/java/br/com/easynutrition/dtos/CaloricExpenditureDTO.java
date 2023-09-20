package br.com.easynutrition.dtos;


import br.com.easynutrition.models.Formula;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CaloricExpenditureDTO {
    private long id;
    private double weight;
    private double height;
    private int age;
    private char sex;
    private double activityFactor;

    @Enumerated
    private Formula formula;

    private double geb;
    private double get;
}
