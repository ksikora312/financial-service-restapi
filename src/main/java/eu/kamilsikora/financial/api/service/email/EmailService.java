package eu.kamilsikora.financial.api.service.email;

import eu.kamilsikora.financial.api.entity.Token;

public interface EmailService {
    void sendAccountActivationEmail(Token token);
}
