package application.service.impl;

import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.DutyService;
import org.apache.commons.lang3.StringUtils;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty, Integer> implements DutyService {
    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }

    @Override
    public Duty findByParentTitle(String title) {
        if (StringUtils.isNotBlank(title)) {
            return repository.findByParentTitle(title);
        }
        return null;
    }
}
