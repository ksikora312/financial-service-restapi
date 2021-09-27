package eu.kamilsikora.financial.api.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameAndPasswordUniquenessValidator.class)
public @interface UniqueUsernameAndEmail {
    String message() default "Username and email must be unique!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
