package eu.kamilsikora.financial.api.controller;

import eu.kamilsikora.financial.api.configuration.auth.LoginCredentials;
import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import eu.kamilsikora.financial.api.service.TokenService;
import eu.kamilsikora.financial.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginCredentials loginCredentials) {
    }

    @GetMapping("/activate/{token}")
    @ResponseStatus(HttpStatus.OK)
    public String activateAccount(@PathVariable("token") String token) {
        tokenService.activateAccountByToken(token);
        return "Account activated successfully!";
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal UserDetails principal) {
        return "hello " + principal.getUsername();
    }
}
