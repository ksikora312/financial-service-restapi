package eu.kamilsikora.financial.api.controller.auth;

import eu.kamilsikora.financial.api.configuration.auth.LoginCredentials;
import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/")
public interface AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody final RegistrationDto registrationDto);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    void login(@RequestBody final LoginCredentials loginCredentials);

    @GetMapping("/activate/{token}")
    @ResponseStatus(HttpStatus.OK)
    String activateAccount(@PathVariable("token") final String token);

}
