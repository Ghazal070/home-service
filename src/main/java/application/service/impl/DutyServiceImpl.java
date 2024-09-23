package application.service.impl;

import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.DutyService;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty,Integer>implements DutyService {
    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }
}
