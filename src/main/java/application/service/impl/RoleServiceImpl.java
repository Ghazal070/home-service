package application.service.impl;

import application.entity.Authority;
import application.entity.Role;
import application.repository.RoleRepository;
import application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    @Transactional
    public void createOrUpdate(String name, Set<Authority> authorities) {
        Role role = repository.findByName(name).orElse(new Role());
        role.setName(name);
        role.setAuthorities(authorities);
        repository.save(role);

    }
}
