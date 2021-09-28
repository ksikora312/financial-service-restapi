package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.Token;

public interface EmailService {
    void sendAccountActivationEmail(Token token);
}
