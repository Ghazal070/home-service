package application.service.impl;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.repository.DutyRepository;
import application.service.CustomerService;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DutyServiceImpl extends BaseEntityServiceImpl<DutyRepository, Duty, Integer> implements DutyService {


    public DutyServiceImpl(Validator validator, DutyRepository repository) {
        super(validator, repository);
    }

    @Override
    public Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty) {
        if (updateDuty != null) {
            //done id not null check in dto
            //done use optional
            Optional<Duty> duty = repository.findById(updateDuty.getDutyId());
            if (duty.isEmpty()) {
                throw new ValidationException("Duty with ID " + updateDuty.getDutyId() +" not found");
            }
            if (updateDuty.getPrice() != null && duty.get().getBasePrice() >= updateDuty.getPrice()){
                throw new ValidationException("Input price equal lower than base price duty");
            }

            int countUpdate = repository.updateDutyPriceOrDescriptionOrSelectable(updateDuty.getDutyId(), updateDuty.getPrice()
                    , updateDuty.getDescription(), updateDuty.getSelectable());
            return countUpdate>0;
        } else throw new ValidationException("Update duty is null");
    }

    @Override
    public List<DutyResponseChildren> loadAllDutyWithChildren() {
        List<Duty> duties = repository.loadAllDutyWithChildren();
        Map<Integer, DutyResponseChildren> dutyResponseChildrenMap = new HashMap<>();
        List<DutyResponseChildren> rootDuties = new ArrayList<>();
        for (Duty duty : duties) {
            DutyResponseChildren responseChildren = DutyResponseChildren.builder()
                    .id(duty.getId())
                    .title(duty.getTitle())
                    .selectable(duty.getSelectable())
                    .subDuty(new HashSet<>())
                    .build();
            dutyResponseChildrenMap.put(duty.getId(), responseChildren);
            if (duty.getParent() == null) {
                rootDuties.add(responseChildren);
            } else {
                DutyResponseChildren parentResponse = dutyResponseChildrenMap.get(duty.getParent().getId());
                if (parentResponse != null) {
                    parentResponse.getSubDuty().add(responseChildren);
                }
            }
        }
        return rootDuties;
    }

    @Override
    public Boolean containByUniqField(String title, Integer parentId){
        return repository.containByUniqField(title,parentId);
    }
    public List<Duty> getSelectableDuties() {
        return repository.findAllBySelectableTrue();
    }
}
