package application.repository;

import application.dto.UpdateDuty;
import application.entity.Duty;

public interface DutyRepository extends BaseEntityRepository<Duty,Integer>{
    Boolean updateDutyPriceOrDescription(Duty duty,UpdateDuty updateDuty);
}
