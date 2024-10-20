package application.service;

import application.dto.DutyByIdDto;
import application.dto.DutyResponseChildrenDto;
import application.dto.UpdateDutyDto;
import application.entity.Duty;

import java.util.List;

public interface DutyService extends BaseEntityService<Duty,Integer> {

    Boolean updateDutyPriceOrDescription(UpdateDutyDto updateDutyDto);
    List<DutyResponseChildrenDto> loadAllDutyWithChildren();
    Boolean containByUniqField(String title, Integer parentId);
    List<Duty> getSelectableDuties();
    Boolean existsByTitle(String dutyTitle);
    DutyByIdDto findByTitle(String title);

}
