package application.config;

import application.entity.Authority;
import application.entity.users.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class CustomUserDetail implements UserDetails {
    private final Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        if (!CollectionUtils.isEmpty(users.getRoles())){
            users.getRoles().forEach(
                    role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                        if (!role.getAuthorities().isEmpty()){
                            role.getAuthorities().forEach(
                                    authority -> {
                                        authorities.add(new SimpleGrantedAuthority(authority.getName()));
                                    }
                            );
                        }
                    }
            );
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getProfile().getPassword();
    }

    @Override
    public String getUsername() {
        return users.getProfile().getEmail();
    }
}
