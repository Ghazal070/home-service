package service.impl;

import entity.users.Expert;
import repository.ExpertRepository;
import service.ExpertService;
import service.PasswordEncode;
import util.AuthHolder;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository, authHolder, passwordEncode);
    }
}
