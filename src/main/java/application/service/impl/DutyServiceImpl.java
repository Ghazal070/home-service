package application.service.impl;

import application.dto.DutyByIdParentIdDto;
import application.dto.DutyResponseChildrenDto;
import application.dto.UpdateDutyDto;
import application.entity.Duty;
import application.repository.DutyRepository;
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
    public Boolean updateDutyPriceOrDescription(UpdateDutyDto updateDutyDto) {
        if (updateDutyDto != null) {
            Optional<Duty> duty = repository.findById(updateDutyDto.getDutyId());
            if (duty.isEmpty()) {
                throw new ValidationException("Duty with ID " + updateDutyDto.getDutyId() +" not found");
            }
            int countUpdate = repository.updateDutyPriceOrDescriptionOrSelectable(updateDutyDto.getDutyId(), updateDutyDto.getPrice()
                    , updateDutyDto.getDescription(), updateDutyDto.getSelectable());
            return countUpdate>0;
        } else throw new ValidationException("Update duty is null");
    }

    @Override
    public List<DutyResponseChildrenDto> loadAllDutyWithChildren() {
        List<Duty> duties = repository.loadAllDutyWithChildren();
        Map<Integer, DutyResponseChildrenDto> dutyResponseChildrenMap = new HashMap<>();
        List<DutyResponseChildrenDto> rootDuties = new ArrayList<>();
        for (Duty duty : duties) {
            DutyResponseChildrenDto responseChildren = DutyResponseChildrenDto.builder()
                    .id(duty.getId())
                    .title(duty.getTitle())
                    .selectable(duty.getSelectable())
                    .subDuty(new HashSet<>())
                    .build();
            dutyResponseChildrenMap.put(duty.getId(), responseChildren);
            if (duty.getParent() == null) {
                rootDuties.add(responseChildren);
            } else {
                DutyResponseChildrenDto parentResponse = dutyResponseChildrenMap.get(duty.getParent().getId());
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

    @Override
    public Boolean existsByTitle(String dutyTitle) {
        return repository.existsByTitle(dutyTitle);
    }

    public List<Duty> getSelectableDuties() {
        return repository.findAllBySelectableTrue();
    }

    @Override
    public DutyByIdParentIdDto findByTitle(String title) {
        return repository.findByTitle(title);
    }
}
