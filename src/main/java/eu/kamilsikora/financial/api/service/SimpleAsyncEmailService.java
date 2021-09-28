package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleAsyncEmailService implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendAccountActivationEmail(Token token) {

    }
}
