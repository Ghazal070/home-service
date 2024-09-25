package application.repository;

import application.dto.UpdateDuty;
import application.entity.Duty;

import java.util.List;

public interface DutyRepository extends BaseEntityRepository<Duty,Integer>{
    Boolean updateDutyPriceOrDescriptionOrSelectable(Duty duty,UpdateDuty updateDuty);
    List<Duty> loadAllDutyWithChildren();
}
