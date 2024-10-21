package application.config;


import application.entity.users.Admin;
import application.service.AdminService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAdminDetailService implements UserDetailsService {

    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> optionalAdmin = adminService.findByEmail(username);
        if (optionalAdmin.isPresent()){
            return new CustomAdminDetail(optionalAdmin.get());
        }
        throw new ValidationException(username + " not found");
    }
}
