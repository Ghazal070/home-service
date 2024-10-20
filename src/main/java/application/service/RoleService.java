package application.service;


import application.entity.Authority;

import java.util.Set;

public interface RoleService{

    void createOrUpdate(String name, Set<Authority> authorities);
}
