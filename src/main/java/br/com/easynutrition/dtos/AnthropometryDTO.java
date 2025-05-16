package br.com.easynutrition.dtos;

import br.com.easynutrition.models.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnthropometryDTO {
    private Long id;
    private Double weight;
    private String height;
    private String sex;
    private Skinfolds skinfolds;
    private BodyCircunferences bodyCircunferences;
    private NutritionalAssessment nutritionalAssessment;
    private Person person;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime evaluationDate;

    public AnthropometryDTO(Anthropometry anthropometry) {
        this.id = anthropometry.getId();
        this.weight = anthropometry.getWeight();
        this.height = String.valueOf(anthropometry.getHeight() / 100);
        this.sex = anthropometry.getSex().name();
        this.skinfolds = anthropometry.getSkinfolds();
        this.bodyCircunferences = anthropometry.getBodyCircunferences();
        this.nutritionalAssessment = anthropometry.getNutritionalAssessment();
        this.person = anthropometry.getPerson();
        this.evaluationDate = anthropometry.getEvaluationDate();
    }
}
