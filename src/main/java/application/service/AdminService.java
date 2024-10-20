package application.service;

import application.dto.DutyCreationDto;
import application.dto.SearchDto;
import application.dto.UsersSearchResponse;
import application.entity.users.Admin;

import java.util.List;

public interface AdminService extends UserService<Admin>{
    void createDuty(DutyCreationDto dutyCreationDto);
    Boolean updateExpertStatus(Integer expertId);
    Boolean addDutyToExpert(Integer expertId, Integer dutyId);
    Boolean removeDutyFromExpert(Integer expertId, Integer dutyId);
    List<UsersSearchResponse> searchUser(SearchDto searchDto);
}
