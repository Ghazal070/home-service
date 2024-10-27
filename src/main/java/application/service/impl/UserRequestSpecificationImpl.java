package application.service.impl;

import application.dto.UserReportFilterAdmin;
import application.dto.projection.UserOrderCount;
import application.entity.users.Users;
import application.entity.users.Users_;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
import application.repository.UserRepository;
import application.service.UserService;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserRequestSpecificationImpl {
    private final UserRepository<Users> userRepository;
    private final UserService<Users> userService;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;

    public List<Users> getUsersByAdminSearch(UserReportFilterAdmin userReportFilterAdmin) {
        List<Predicate> predicates = new ArrayList<>();
        return userRepository.findAll(
                (root, query, cb) -> {
                    fillRegistrationDate(predicates, root, cb
                            , userReportFilterAdmin.getRegisterDateStart(), userReportFilterAdmin.getRegisterDateEnd());
                    fillRequestCounts(userReportFilterAdmin.getMinTotalRequests(), userReportFilterAdmin.getMaxTotalRequests());
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        );

    }

    private void fillRegistrationDate(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String registerDateStart, String registerDateEnd) {
        if (registerDateStart != null && registerDateEnd != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION)
                            , registerDateStart, registerDateEnd)
            );
        } else if (registerDateStart != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION)
                            , registerDateStart, String.valueOf(LocalDateTime.now()))
            );
        } else if (registerDateEnd != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION),
                            String.valueOf(LocalDateTime.now().minusMonths(2L)), registerDateEnd)
            );
        }
    }

    private void getExpertCounts(Integer minRequests, Integer maxRequests) {
        List<UserOrderCount> expertOrderCounts = expertRepository.getExpertOrderCounts();
        if (minRequests != null && maxRequests != null) {
            List<UserOrderCount> minMaxRequests = expertOrderCounts.stream().filter(a ->
                    a.getTotalRequests() > minRequests && a.getTotalRequests() < maxRequests).toList();
        } else if (minRequests != null) {
            List<UserOrderCount> minMaxRequests = expertOrderCounts.stream().filter(a ->
                    a.getTotalRequests() > minRequests).toList();
        } else if (maxRequests != null) {
            List<UserOrderCount> minMaxRequest = expertOrderCounts.stream().filter(a ->
                    a.getTotalRequests() < maxRequests).toList();
        }
    }

    private void getCustomerCounts(Integer minRequests, Integer maxRequests) {
        List<UserOrderCount> customerOrderCounts = customerRepository.getCustomerOrderCounts();
        if (minRequests != null && maxRequests != null) {
            List<UserOrderCount> minMaxRequests = customerOrderCounts.stream().filter(a ->
                    a.getTotalRequests() > minRequests && a.getTotalRequests() < maxRequests).toList();
        } else if (minRequests != null) {
            List<UserOrderCount> minMaxRequests = customerOrderCounts.stream().filter(a ->
                    a.getTotalRequests() > minRequests).toList();
        } else if (maxRequests != null) {
            List<UserOrderCount> minMaxRequest = customerOrderCounts.stream().filter(a ->
                    a.getTotalRequests() < maxRequests).toList();
        }
    }
public void getOrderMerge(){

}
}
