package br.com.easynutrition.dtos;

import br.com.easynutrition.models.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsersRegisterDTO {
    @NotBlank(message = "O campo email não pode ser nulo.")
    private String email;

    @NotBlank(message = "O campo password não pode ser nulo.")
    private String password;

    @NotBlank(message = "O campo fullName não pode ser nulo.")
    private String fullName;

    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    private String cpf;

    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter 11 dígitos numéricos.")
    private String phone;

    private UsersRole usersRole;

    private LocalDate registrationDate;
}
