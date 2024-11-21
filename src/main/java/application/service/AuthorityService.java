package application.service;


import application.entity.Authority;

import java.util.List;
import java.util.Set;

public interface AuthorityService {

    void createIfNotExist(String[] name);

    Set<Authority> findByNames(List<String> names);

}
