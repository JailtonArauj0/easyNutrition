package br.com.easynutrition.api.dto.response.Anthropometry;

import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import lombok.Getter;

@Getter
public class BodyFatClassificationDTO {
    private final Double bodyFat;
    private final String bfClassification;

    public BodyFatClassificationDTO(Double bodyFat, String bfClassification) {
        this.bodyFat = bodyFat;
        this.bfClassification = bfClassification;
    }

    public BodyFatClassificationDTO(Anthropometry anthropometry) {
        this.bodyFat = anthropometry.getNutritionalAssessment().getBodyFat();
        this.bfClassification = anthropometry.getNutritionalAssessment().getBfClassification();
    }
}
