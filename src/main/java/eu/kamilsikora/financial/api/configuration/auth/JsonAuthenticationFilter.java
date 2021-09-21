package eu.kamilsikora.financial.api.configuration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        try {
            final BufferedReader reader = request.getReader();
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            final LoginCredentials authRequest = objectMapper.readValue(builder.toString(), LoginCredentials.class);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword());
            super.setDetails(request, token);
            return super.getAuthenticationManager().authenticate(token);
        } catch (final IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
