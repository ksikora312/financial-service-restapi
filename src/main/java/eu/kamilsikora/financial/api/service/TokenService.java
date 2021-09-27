package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.Token;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.errorhandling.ActivationTokenException;
import eu.kamilsikora.financial.api.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void activateAccountByToken(String tokenValue) {
        Token token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new ActivationTokenException("Token is not connected with user!"));
        if(token.hasExpired()) {
            throw new ActivationTokenException("Token has already expired!");
        }
        User user = token.getUser();
        if(user.getEnabled()) {
            throw new ActivationTokenException("User account is already activated!");
        }
        user.activateAccount();
        tokenRepository.delete(token);
    }
}
