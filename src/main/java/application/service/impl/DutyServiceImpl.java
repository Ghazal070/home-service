package application.service.impl;

import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.DutyType;
import application.repository.DutyRepository;
import application.service.DutyService;
import application.service.DutyTypeService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty, Integer> implements DutyService {
    private final DutyTypeService dutyTypeService;

    public DutyServiceImpl(DutyRepository repository, DutyTypeService dutyTypeService) {
        super(repository);
        this.dutyTypeService = dutyTypeService;
    }

    @Override
    public Duty findByDutyTypeTitle(String title) {
        if (StringUtils.isNotBlank(title)) {
            return repository.findByDutyTypeTitle(title);
        }
        return null;
    }

    @Override
    public Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty) {
        if (updateDuty != null) {
            if (StringUtils.isNotBlank(updateDuty.getTitle())) {
                Duty duty = repository.findByDutyTypeTitle(updateDuty.getTitle());
                if (duty != null) {
                    if (duty.getBasePrice() != null) {
                        if (updateDuty.getPrice() > duty.getBasePrice()) {
                            return repository.updateDutyPriceOrDescription(duty, updateDuty);
                        } else
                            throw new ValidationException("updateDuty.getPrice must be greater than duty.getBasePrice");
                    }else return repository.updateDutyPriceOrDescription(duty, updateDuty);
                } else throw new ValidationException("Duty findBy title is null");
            } else throw new ValidationException("Update duty title is null");
        } else throw new ValidationException("Update duty is null");
    }
}
