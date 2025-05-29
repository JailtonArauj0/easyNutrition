package br.com.easynutrition.dtos.response.Anthropometry;

import br.com.easynutrition.models.Anthropometry.Anthropometry;
import lombok.Getter;

@Getter
public class WhrClassificationDTO {
    private final Double whr;
    private final String whrClassification;

    public WhrClassificationDTO(Double whr, String whrClassification) {
        this.whr = whr;
        this.whrClassification = whrClassification;
    }

    public WhrClassificationDTO(Anthropometry anthropometry) {
        this.whr = anthropometry.getNutritionalAssessment().getWhr();
        this.whrClassification = anthropometry.getNutritionalAssessment().getWhrClassification();
    }

}
