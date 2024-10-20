package application.config;

import application.entity.users.Users;
import application.exception.ValidationException;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService<T extends Users> implements UserDetailsService {

    private final UserService<T> userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userService.findByEmail(username);
        if (optionalUsers.isPresent()){
            return new CustomUserDetail(optionalUsers.get());
        }
        throw new ValidationException(username + " not found");
    }
}
