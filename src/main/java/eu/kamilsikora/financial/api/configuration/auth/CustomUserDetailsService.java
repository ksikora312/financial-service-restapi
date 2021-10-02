package eu.kamilsikora.financial.api.configuration.auth;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USER_WITH_EMAIL_DOES_NOT_EXIST_ERROR_MESSAGE =
            "User with specified email does not exist!";
    private static final String USER_WITH_USERNAME_DOES_NOT_EXIST_ERROR_MESSAGE =
            "User with specified username does not exist!";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if(isLoggingWithEmail(username)) {
            user = provideUserByEmail(username);
        }
        else {
            user = provideUserByUsername(username);
        }
        return new UserPrincipal(user);
    }

    private boolean isLoggingWithEmail(String potentiallyEmail) {
        //TODO: naive solution. Think of better one in future
        return potentiallyEmail.contains("@");
    }

    private User provideUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(USER_WITH_EMAIL_DOES_NOT_EXIST_ERROR_MESSAGE));
    }

    private User provideUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(USER_WITH_USERNAME_DOES_NOT_EXIST_ERROR_MESSAGE));
    }

}
