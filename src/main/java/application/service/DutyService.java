package application.service;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;

import java.util.List;
import java.util.Optional;

public interface DutyService extends BaseEntityService<Duty,Integer> {

    Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty);
    List<DutyResponseChildren> loadAllDutyWithChildren();
    Boolean containByUniqField(String title, Integer parentId);
    List<Duty> getSelectableDuties();
    Boolean existsByTitle(String dutyTitle);
}
