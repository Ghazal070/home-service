package application.service;

import application.entity.Duty;
import application.entity.DutyType;
import application.entity.users.Admin;

public interface AdminService extends UserService<Admin>{
    DutyType createDutyType(String dutyTypeTitle);
//    Duty createDuty(String dutyTypeTitle);
}
