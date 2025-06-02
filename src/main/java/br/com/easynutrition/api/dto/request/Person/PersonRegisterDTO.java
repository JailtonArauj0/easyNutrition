package br.com.easynutrition.api.dto.request.Person;

import br.com.easynutrition.domain.model.Person.Person;
import br.com.easynutrition.domain.model.User.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonRegisterDTO {
    @NotBlank(message = "O campo 'firstName' não pode ser nulo.")
    private String firstName;

    private String lastName;

    @Pattern(regexp = "\\d{11}", message = "O campo 'phone' deve conter 11 dígitos numéricos.")
    private String phone;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "O campo 'cpf' deve conter 11 dígitos numéricos.")
    private String cpf;

    @NotNull(message = "O campo 'birthDate' não pode ser nulo.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotNull(message = "O campo 'nutritionistId' não pode ser nulo.")
    private Long nutritionistId;

    public Person toEntity(Users nutritionist) {
        int age = LocalDate.now().getYear() - this.getBirthDate().getYear();

        return Person.builder()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .age(age)
                .phone(this.getPhone())
                .cpf(this.getCpf())
                .birthDate(this.getBirthDate())
                .nutritionist(nutritionist)
                .build();
    }
}
