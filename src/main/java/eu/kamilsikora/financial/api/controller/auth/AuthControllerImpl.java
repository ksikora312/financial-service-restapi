package eu.kamilsikora.financial.api.controller.auth;

import eu.kamilsikora.financial.api.configuration.auth.LoginCredentials;
import eu.kamilsikora.financial.api.dto.CheckResponseDto;
import eu.kamilsikora.financial.api.dto.auth.RegistrationDto;
import eu.kamilsikora.financial.api.service.TokenService;
import eu.kamilsikora.financial.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public void register(final RegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
    }

    public void login(final LoginCredentials loginCredentials) {
    }

    public String activateAccount(final String token) {
        tokenService.activateAccountByToken(token);
        return "Account activated successfully!";
    }

    @Override
    public CheckResponseDto isUsernameTaken(final String username) {
        final boolean isTaken = userService.isUsernameTaken(username);
        return new CheckResponseDto(!isTaken);
    }

    @Override
    public CheckResponseDto isEmailTaken(final String email) {
        final boolean isTaken = userService.isEmailTaken(email);
        return new CheckResponseDto(!isTaken);
    }
}
