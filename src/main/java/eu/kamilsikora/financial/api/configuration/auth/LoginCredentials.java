package eu.kamilsikora.financial.api.configuration.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCredentials {
    private String login;
    private String password;
}
