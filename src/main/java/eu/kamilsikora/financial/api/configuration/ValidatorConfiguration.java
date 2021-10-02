package eu.kamilsikora.financial.api.configuration;

import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public ExceptionThrowingValidator validator(final Validator validator) {
        return new ExceptionThrowingValidator(validator);
    }

}
