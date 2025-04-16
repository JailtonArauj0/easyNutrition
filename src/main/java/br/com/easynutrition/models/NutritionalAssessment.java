package br.com.easynutrition.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class NutritionalAssessment {

    @Column(nullable = true)
    private double bmi;

    @Column(nullable = true)
    private String bmiClassification;

    @Column(nullable = true)
    private double whr;

    @Column(nullable = true)
    private String whrClassification;

    @Column(nullable = true)
    private double bodyFat;

    @Column(nullable = true)
    private String bfClassification;
}
