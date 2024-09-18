package service.impl;

import entity.users.Expert;
import repository.ExpertRepository;
import service.ExpertService;
import util.AuthHolder;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, AuthHolder authHolder) {
        super(repository, authHolder);
    }
}
