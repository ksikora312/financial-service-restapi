package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.mapper.UserMapper;
import eu.kamilsikora.financial.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Validator validator;

    public void registerUser(RegistrationDto registrationDto) {
        User user = userMapper.convertToEntity(registrationDto);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        constraintViolations.stream().forEach(v -> System.out.println(v.getMessage()));
        //TODO: add some constraints checks
        userRepository.save(user);
    }

}
