package br.com.easynutrition.dtos;

import br.com.easynutrition.models.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsersRegisterDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
    @Size(min = 11, max = 11)
    private String cpf;
    @Size(min = 11, max = 11)
    private String phone;

    private UsersRole usersRole;

    private LocalDate registrationDate;
}
