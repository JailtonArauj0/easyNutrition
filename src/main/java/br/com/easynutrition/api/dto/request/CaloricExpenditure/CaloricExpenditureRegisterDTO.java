package br.com.easynutrition.api.dto.request.CaloricExpenditure;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaloricExpenditureRegisterDTO {
    @NotNull
    private Double activityFactor;
    @Min(value = 1, message = "O ID da Antropometria deve ser um número positivo.")
    private Long anthropometryId;
}
