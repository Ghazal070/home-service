package application.service;

import application.dto.UpdateDuty;
import application.entity.Duty;

public interface DutyService extends BaseEntityService<Duty,Integer> {

    Duty findByDutyTypeTitle(String title);
    Boolean updateDutyPriceOrDescription(UpdateDuty updateDuty);

}
