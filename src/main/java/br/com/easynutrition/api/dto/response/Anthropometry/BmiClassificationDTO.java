package br.com.easynutrition.api.dto.response.Anthropometry;

import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BmiClassificationDTO {
    private Double bmi;
    private String bmiClassification;

    public BmiClassificationDTO(Double bmi, String bmiClassification) {
        this.bmi = bmi;
        this.bmiClassification = bmiClassification;
    }

    public BmiClassificationDTO(Anthropometry anthropometry) {
        this.bmi = anthropometry.getNutritionalAssessment().getBmi();
        this.bmiClassification = anthropometry.getNutritionalAssessment().getBmiClassification();
    }
}
