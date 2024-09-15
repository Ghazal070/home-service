package service.impl;

import entity.Duty;
import repository.DutyRepository;
import service.DutyService;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty,Integer>implements DutyService {
    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }
}
