package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.mapper.UserMapper;
import eu.kamilsikora.financial.api.repository.UserRepository;
import eu.kamilsikora.financial.api.util.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ExceptionThrowingValidator validator;

    public void registerUser(RegistrationDto registrationDto) {
        User user = userMapper.convertToEntity(registrationDto);
        validator.validate(user, registrationDto);
        //TODO: add some constraints checks
        userRepository.save(user);
    }

}
