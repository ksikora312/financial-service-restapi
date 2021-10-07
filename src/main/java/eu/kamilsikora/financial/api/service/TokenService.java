package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.Token;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.errorhandling.ActivationTokenException;
import eu.kamilsikora.financial.api.repository.ExpensesRepository;
import eu.kamilsikora.financial.api.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final TemporalUnit TOKEN_ALIVE_UNIT = ChronoUnit.DAYS;
    private static final long TOKEN_ALIVE_VALUE = 7L;

    private final TokenRepository tokenRepository;
    private final ExpensesRepository expensesRepository;

    @Transactional
    public void activateAccountByToken(final String tokenValue) {
        final Token token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new ActivationTokenException("Token is not connected with user!"));
        if (token.hasExpired()) {
            throw new ActivationTokenException("Token has already expired!");
        }
        final User user = token.getUser();
        if (user.getEnabled()) {
            throw new ActivationTokenException("User account is already activated!");
        }
        activateUserAccount(user);
        createExpensesForUser(user);
        tokenRepository.delete(token);
    }

    private void activateUserAccount(final User user) {
        user.setEnabled(true);
        user.setActivationDate(LocalDateTime.now());
    }

    private void createExpensesForUser(final User user) {
        Expenses expenses = new Expenses();
        expenses.setUser(user);
        expensesRepository.save(expenses);
    }

    @Transactional
    public Token createActivationToken(final User user) {
        final Token token = new Token(user, TOKEN_ALIVE_UNIT, TOKEN_ALIVE_VALUE);
        return tokenRepository.save(token);
    }

}
