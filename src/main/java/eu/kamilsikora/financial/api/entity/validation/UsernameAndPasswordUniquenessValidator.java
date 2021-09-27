package eu.kamilsikora.financial.api.entity.validation;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsernameAndPasswordUniquenessValidator implements ConstraintValidator<UniqueUsernameAndEmail, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsernameAndEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        List<User> alreadyInDbUsers = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        Set<String> errors = checkWhetherUsernameAndPasswordAreUnique(user, alreadyInDbUsers);
        errors.forEach(error ->
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation());
        return errors.isEmpty();
    }

    private Set<String> checkWhetherUsernameAndPasswordAreUnique(User user, List<User> alreadyInDbUsers) {
        Set<String> errors = new HashSet<>();
        for (User userInDb : alreadyInDbUsers) {
            if (userInDb.getUsername().equals(user.getUsername())) {
                errors.add(String.format("Given username %s already exist! ", user.getUsername()));
            }
            if (userInDb.getEmail().equals(user.getEmail())) {
                errors.add(String.format("There is already an account associated with email %s! ", user.getEmail()));
            }
        }
        return errors;
    }
}
