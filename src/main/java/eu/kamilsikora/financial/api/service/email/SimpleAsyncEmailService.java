package eu.kamilsikora.financial.api.service.email;

import eu.kamilsikora.financial.api.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleAsyncEmailService implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    @Async
    public void sendAccountActivationEmail(Token token) {
        // TODO: consider dynamically obtaining url for activation endpoint
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("financial.management-app@noreply.com");
        simpleMailMessage.setTo(token.getUser().getEmail());
        simpleMailMessage.setSubject("Financial management app - Account activation");
        simpleMailMessage.setText("Click link to activate an account: \n" +
                "<a href=http://localhost:8080/finance-management/api/activate/" + token.getToken() + "/>");
        emailSender.send(simpleMailMessage);
    }
}
