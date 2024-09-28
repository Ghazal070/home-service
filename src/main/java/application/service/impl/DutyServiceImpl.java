package application.service.impl;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty, Integer> implements DutyService {

    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }

    @Override
    public Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty) {
        if (updateDuty != null) {
            //done id not null check in dto
            //done use optional
            Optional<Duty> optionalDuty = Optional.ofNullable(repository.findById(updateDuty.getDutyId()));
            if (!optionalDuty.isPresent()) {
                throw new ValidationException("Duty with ID " + updateDuty.getDutyId() + " not found.");
            }
            Duty duty = optionalDuty.get();
            return repository.updateDutyPriceOrDescriptionOrSelectable(duty, updateDuty);
        } else throw new ValidationException("Update duty is null");
    }

    @Override
    public List<DutyResponseChildren> loadAllDutyWithChildren() {
        return repository.loadAllDutyWithChildren();
    }

    @Override
    public Boolean containByUniqField(String title, Integer parentId){
        return repository.containByUniqField(title,parentId);
    }
}
