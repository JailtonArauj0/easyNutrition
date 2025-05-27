package br.com.easynutrition.models.Anthropometry;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class NutritionalAssessment {
    private Double bmi;
    private String bmiClassification;
    private Double whr;
    private String whrClassification;
    private Double bodyFat;
    private String bfClassification;
}
