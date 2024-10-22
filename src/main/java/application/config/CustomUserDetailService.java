package application.config;


import application.entity.users.Admin;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import application.service.UserService;
import application.service.impl.AdminServiceImpl;
import application.service.impl.CustomerServiceImpl;
import application.service.impl.ExpertServiceImpl;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService<T extends Users> implements UserDetailsService {

    @Autowired
    private ApplicationContext context;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserService<T> userService = getUserServiceForUsername(username);
        Optional<T> optionalUser = userService.findByEmail(username);
        if (optionalUser.isPresent()) {
            return new CustomUserDetail(optionalUser.get());
        }
        throw new ValidationException(username + " not found");
    }

    private UserService<T> getUserServiceForUsername(String username) {
        if (Users.class.isAssignableFrom(Admin.class)) {
            return (UserService<T>) context.getBean(AdminServiceImpl.class);
        } else if (Users.class.isAssignableFrom(Customer.class)) {
            return (UserService<T>) context.getBean(CustomerServiceImpl.class);
        } else if (Users.class.isAssignableFrom(Expert.class)) {
            return (UserService<T>) context.getBean(ExpertServiceImpl.class);
        }

        throw new ValidationException("No suitable UserService found for username: " + username);
    }
}
