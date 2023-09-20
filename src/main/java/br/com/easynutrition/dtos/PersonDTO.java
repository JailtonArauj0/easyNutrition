package br.com.easynutrition.dtos;

import br.com.easynutrition.models.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonDTO {
    private long id;

    @NotBlank
    private String firstName;

    private String lastName;

    private int age;

    @Size(min = 11, max = 11)
    private String phone;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        this.phone = person.getPhone();
        this.cpf = person.getCpf();
        this.birthDate = person.getBirthDate();
    }
}
