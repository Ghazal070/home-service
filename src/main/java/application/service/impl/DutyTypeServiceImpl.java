package application.service.impl;

import application.entity.DutyType;
import application.repository.DutyTypeRepository;
import application.service.DutyTypeService;

public class DutyTypeServiceImpl extends BaseEntityServiceImpl<DutyTypeRepository, DutyType,Integer>
        implements DutyTypeService {

    public DutyTypeServiceImpl(DutyTypeRepository repository) {
        super(repository);
    }
}
