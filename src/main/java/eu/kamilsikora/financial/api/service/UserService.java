package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.controller.dto.RegistrationDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.mapper.UserMapper;
import eu.kamilsikora.financial.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void registerUser(RegistrationDto registrationDto) {
        User user = userMapper.convertToEntity(registrationDto);
        userRepository.save(user);
    }

}
