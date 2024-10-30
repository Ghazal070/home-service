package application.service;

import application.dto.SearchDto;
import application.dto.UserOrderCountReportDto;
import application.dto.UserReportFilterAdmin;
import application.entity.users.Users;

import java.util.List;

public interface UserRequestSpecification {
    List<UserOrderCountReportDto> getUsersByAdminSearch(UserReportFilterAdmin userReportFilterAdmin);
}
