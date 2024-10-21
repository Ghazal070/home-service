package application.config;

import application.entity.users.Admin;
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
public class CustomAdminDetail implements UserDetails {
    private final Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        if (!CollectionUtils.isEmpty(admin.getRoles())){
            admin.getRoles().forEach(
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
        return admin.getProfile().getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getProfile().getEmail();
    }
}
