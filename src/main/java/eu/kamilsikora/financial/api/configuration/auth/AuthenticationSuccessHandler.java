package eu.kamilsikora.financial.api.configuration.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int expirationTime;
    private final String secret;
    private final ObjectMapper objectMapper;

    public AuthenticationSuccessHandler(@Value("${jwt.expirationTime:360000}") final int expirationTime,
                                        @Value("${jwt.secret}") final String secret,
                                        final ObjectMapper objectMapper) {
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        final String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        response.getOutputStream().print(objectMapper.writeValueAsString(new TokenResponse(token)));
    }

    @Getter
    @Setter
    private static class TokenResponse {

        private static final String tokenPrefix = "Bearer ";

        public TokenResponse(final String token) {
            this.token = tokenPrefix + token;
        }

        private String token;
    }
}
