package br.com.easynutrition.dtos;

import br.com.easynutrition.models.BodyCircunferences;
import br.com.easynutrition.models.NutritionalAssessment;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.models.Skinfolds;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnthropometryDTO {
    private long id;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate evaluationDate;

    private double weight;
    private double height;
    private char sex;
    private Skinfolds skinfolds;
    private BodyCircunferences bodyCircunferences;
    private NutritionalAssessment nutritionalAssessment;

    @NotNull
    private Person person;
}
