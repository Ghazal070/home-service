package application.service;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.users.Admin;

public interface AdminService extends UserService<Admin>{
    Duty createDuty(DutyCreation dutyCreation);
}
