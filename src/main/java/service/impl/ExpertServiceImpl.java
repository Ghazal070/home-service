package service.impl;

import entity.users.Expert;
import repository.ExpertRepository;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert>{
    public ExpertServiceImpl(ExpertRepository repository) {
        super(repository);
    }
}
