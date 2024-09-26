package application.service;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;

import java.util.List;

public interface DutyService extends BaseEntityService<Duty,Integer> {

    Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty);
    List<DutyResponseChildren> loadAllDutyWithChildren();

}
