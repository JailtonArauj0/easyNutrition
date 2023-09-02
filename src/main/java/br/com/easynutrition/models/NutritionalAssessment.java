package br.com.easynutrition.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class NutritionalAssessment{

    private double bmi;
    private String bmiClassification;
    private double whr;
    private String whrClassification;
    private double bodyFat;
    private String bfClassification;

    //EMBEDDED
}
