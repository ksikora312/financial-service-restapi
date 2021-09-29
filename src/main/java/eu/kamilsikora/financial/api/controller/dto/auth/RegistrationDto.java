package eu.kamilsikora.financial.api.controller.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationDto {
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 5, message = "Password must contain at least 5 characters!")
    private String password;
    @NotBlank(message = "Email cannot be empty!")
    private String email;
}
