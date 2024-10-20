package application.service.impl;

import application.entity.Authority;
import application.repository.AuthorityRepository;
import application.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repository;


    @Override
    @Transactional
    public void createIfNotExist(String[] names) {
        for (String authorityName:names) {
            if(!repository.existsByName(authorityName)){
                repository.save(
                        Authority.builder()
                                .name(authorityName)
                                .build()
                );
            }
        }
    }

    @Override
    public Set<Authority> findByNames(List<String> names) {

        return repository.findAllByNameIsIn(names);
    }
}
