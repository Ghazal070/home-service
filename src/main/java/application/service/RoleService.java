package application.service;


import application.entity.Authority;
import application.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface RoleService{

    void createOrUpdate(String name, Set<Authority> authorities);
    Optional<Role> findByName (String name);
}
