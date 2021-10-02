package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.controller.dto.auth.RegistrationDto;
import eu.kamilsikora.financial.api.entity.Token;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.mapper.UserMapper;
import eu.kamilsikora.financial.api.repository.UserRepository;
import eu.kamilsikora.financial.api.service.email.EmailService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final ExceptionThrowingValidator validator;

    @Transactional
    public void registerUser(final RegistrationDto registrationDto) {
        final User user = userMapper.convertToEntity(registrationDto);
        validator.validate(user, registrationDto);
        userRepository.save(user);
        final Token activationToken = tokenService.createActivationToken(user);
        emailService.sendAccountActivationEmail(activationToken);
    }

}
