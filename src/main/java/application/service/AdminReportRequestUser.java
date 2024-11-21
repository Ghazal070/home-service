package application.service;

import application.dto.projection.UserOrderCount;

import java.util.List;

public interface AdminReportRequestUser {

    List<UserOrderCount> getCustomerReportRequest();
    List<UserOrderCount> getExpertReportRequest();
    List<UserOrderCount> mergeUserReportRequest();
}
