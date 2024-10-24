package application.service.impl;

import application.dto.projection.UserOrderCount;
import application.service.AdminReportRequestUser;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportRequestUserImpl implements AdminReportRequestUser {


    private final CustomerService customerService;
    private final OrderService orderService;
    private final ExpertService expertService;

    @Override
    public List<UserOrderCount> getCustomerReportRequest() {
            return customerService.getCustomerOrderCounts();
    }

    @Override
    public List<UserOrderCount> getExpertReportRequest() {
        return expertService.getExpertOrderCounts();
    }

    @Override
    public List<UserOrderCount> mergeUserReportRequest() {
        List<UserOrderCount> expertReportRequest = getExpertReportRequest();
        List<UserOrderCount> customerReportRequest = getCustomerReportRequest();
        expertReportRequest.addAll(customerReportRequest);
        return expertReportRequest;
    }
}
