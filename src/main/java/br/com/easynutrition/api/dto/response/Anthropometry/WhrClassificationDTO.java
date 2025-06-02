package br.com.easynutrition.api.dto.response.Anthropometry;

import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
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
