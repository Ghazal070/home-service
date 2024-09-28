package application.service;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Admin;
import application.entity.users.Expert;

public interface AdminService extends UserService<Admin>{
    Duty createDuty(DutyCreation dutyCreation);
    Boolean updateExpertStatus(Integer expertId);
    Boolean addDutyToExpert(Integer expertId, Integer dutyId);
    Boolean removeDutyFromExpert(Integer expertId, Integer dutyId);
}
