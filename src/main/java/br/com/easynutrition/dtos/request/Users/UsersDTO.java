package br.com.easynutrition.dtos.request.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
