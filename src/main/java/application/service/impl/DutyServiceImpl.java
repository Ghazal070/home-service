package application.service.impl;

import application.dto.UpdateDuty;
import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty, Integer> implements DutyService {

    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }

    @Override
    public Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty) {
        if (updateDuty != null) {
            if (StringUtils.isNotBlank(updateDuty.getTitle())) {
                Duty duty = repository.findByUniqId(updateDuty.getTitle());
                if (duty != null) {
                    return repository.updateDutyPriceOrDescription(duty, updateDuty);
                } else throw new ValidationException("Duty findBy title is null");
            } else throw new ValidationException("Update duty title is null");
        } else throw new

                ValidationException("Update duty is null");
    }
}
