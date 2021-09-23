package eu.kamilsikora.financial.api.controller;

import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import eu.kamilsikora.financial.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
    }

//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public void login(@RequestBody LoginCredentials loginCredentials) {
//    }

    @GetMapping("/test")
    public String test() {
        return "test content";
    }
}
