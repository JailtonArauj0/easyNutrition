package br.com.easynutrition.dtos.request.Users;

import br.com.easynutrition.enums.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

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
}
