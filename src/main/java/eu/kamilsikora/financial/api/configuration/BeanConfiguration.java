package eu.kamilsikora.financial.api.configuration;

import eu.kamilsikora.financial.api.service.outcome.OutcomeOverviewFactory;
import eu.kamilsikora.financial.api.service.outcome.OverviewProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Properties;

@Configuration
public class BeanConfiguration {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(System.getenv("mail"));
        mailSender.setPassword(System.getenv("password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public OutcomeOverviewFactory overviewFactory(List<OverviewProvider> overviewProviders) {
        return new OutcomeOverviewFactory(overviewProviders);
    }

}
