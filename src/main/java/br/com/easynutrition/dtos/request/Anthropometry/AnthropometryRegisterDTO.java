package br.com.easynutrition.dtos.request.Anthropometry;

import br.com.easynutrition.enums.Gender;
import br.com.easynutrition.models.Anthropometry.Anthropometry;
import br.com.easynutrition.models.Anthropometry.BodyCircunferences;
import br.com.easynutrition.models.Anthropometry.NutritionalAssessment;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.models.Skinfolds;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnthropometryRegisterDTO {
    @NotNull
    private Double weight;

    @NotNull
    private Integer height;

    @NotNull
    @Pattern(regexp = "MASCULINO|FEMININO", message = "Sexo deve ser 'MASCULINO' ou 'FEMININO'")
    private String sex;

    private Skinfolds skinfolds;

    @NotNull
    private BodyCircunferences bodyCircunferences;

    private NutritionalAssessment nutritionalAssessment;

    @NotNull
    private Person person;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate evaluationDate;

    public Anthropometry toEntity() {
        return new Anthropometry(weight, height, Gender.valueOf(sex), skinfolds, bodyCircunferences, nutritionalAssessment, person, evaluationDate);
    }
}
