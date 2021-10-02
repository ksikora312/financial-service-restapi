package eu.kamilsikora.financial.api.service;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHelperService {
    private final UserRepository userRepository;

    public User getActiveUser(Long userId) {
        return userRepository.findByIdAndEnabled(userId, true).orElseThrow(()
                -> new ObjectDoesNotExistException("User does not exist!"));
    }
}
