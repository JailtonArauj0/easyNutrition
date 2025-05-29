package br.com.easynutrition.dtos.response.Anthropometry;

import br.com.easynutrition.models.Anthropometry.Anthropometry;
import br.com.easynutrition.models.Anthropometry.BodyCircunferences;
import br.com.easynutrition.models.Anthropometry.NutritionalAssessment;
import br.com.easynutrition.models.Skinfolds;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AnthropometryDTO {
    private final Long id;
    private final Double weight;
    private final Integer height;
    private final String sex;
    private final Skinfolds skinfolds;
    private final BodyCircunferences bodyCircunferences;
    private final NutritionalAssessment nutritionalAssessment;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private final LocalDate evaluationDate;

    public AnthropometryDTO(Anthropometry anthropometry) {
        this.id = anthropometry.getId();
        this.weight = anthropometry.getWeight();
        this.height = anthropometry.getHeight();
        this.sex = anthropometry.getSex().name();
        this.skinfolds = anthropometry.getSkinfolds();
        this.bodyCircunferences = anthropometry.getBodyCircunferences();
        this.nutritionalAssessment = anthropometry.getNutritionalAssessment();
        this.evaluationDate = anthropometry.getEvaluationDate();
    }
}
