package br.com.easynutrition.api.dto.request.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
