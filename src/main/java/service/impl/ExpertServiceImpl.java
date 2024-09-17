package service.impl;

import entity.users.Expert;
import repository.ExpertRepository;
import service.ExpertService;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository) {
        super(repository);
    }
}
