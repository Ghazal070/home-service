package application.service;

import application.dto.DutyCreationDto;
import application.entity.Duty;
import application.entity.users.Admin;

public interface AdminService extends UserService<Admin>{
    Duty createDuty(DutyCreationDto dutyCreationDto);
    Boolean updateExpertStatus(Integer expertId);
    Boolean addDutyToExpert(Integer expertId, Integer dutyId);
    Boolean removeDutyFromExpert(Integer expertId, Integer dutyId);
}
