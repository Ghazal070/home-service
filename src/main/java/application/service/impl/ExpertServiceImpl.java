package application.service.impl;

import application.entity.users.Expert;
import application.repository.ExpertRepository;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository, authHolder, passwordEncode);
    }
}
