package application.service;

import application.entity.Duty;

public interface DutyService extends BaseEntityService<Duty,Integer> {

    Duty findByParentTitle(String title);
}
