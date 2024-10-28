package application.service.impl;

import application.constants.RoleNames;
import application.dto.UserOrderCountReportDto;
import application.dto.UserReportFilterAdmin;
import application.dto.projection.UserOrderCount;
import application.entity.Role_;
import application.entity.users.Users;
import application.entity.users.Users_;
import application.mapper.UserOrderReportMapper;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
import application.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserRequestSpecificationImpl {
    private final UserRepository<Users> userRepository;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;
    private final UserOrderReportMapper userOrderReportMapper;

    public List<UserOrderCountReportDto> getUsersByAdminSearch(UserReportFilterAdmin userReportFilterAdmin) {
        List<Predicate> predicates = new ArrayList<>();
        List<Users> users = userRepository.findAll(
                (root, query, cb) -> {
                    fillRegistrationDate(predicates, root, cb
                            , userReportFilterAdmin.getRegisterDateStart(), userReportFilterAdmin.getRegisterDateEnd());
                    fillRequestCounts(predicates, root, cb, userReportFilterAdmin.getMinTotalRequests(), userReportFilterAdmin.getMaxTotalRequests());
                    fillDoneCounts(predicates, root, cb, userReportFilterAdmin.getMinDoneOrders(), userReportFilterAdmin.getMaxDoneOrders());
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        );
        List<UserOrderCount> orderCounts = getOrderCountsForUsers(users);
        List<UserOrderCountReportDto> dtoList = new ArrayList<>();
        for (Users user : users) {
            Optional<UserOrderCount> orderCount = findOrderCountForUser(user.getId(), orderCounts);
            UserOrderCountReportDto dto = userOrderReportMapper.convertEntityToDto(user,orderCount);
            dtoList.add(dto);
        }

        return dtoList;
    }


    private List<UserOrderCount> getOrderCountsForUsers(List<Users> users) {

        return customerRepository.getCustomerOrderCounts();
    }

    private Optional<UserOrderCount> findOrderCountForUser(Integer userId, List<UserOrderCount> orderCounts) {
        return orderCounts.stream()
                .filter(orderCount -> orderCount.getUserId().equals(userId))
                .findFirst();
    }


    private void fillRegistrationDate(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String registerDateStart, String registerDateEnd) {
        LocalDateTime startDateTime=null;
        LocalDateTime endDateTime=null;
        if (registerDateStart!=null){
            startDateTime = LocalDateTime.parse(registerDateStart);
        }
        if (registerDateEnd!=null){
            endDateTime = LocalDateTime.parse(registerDateEnd);
        }
        if (registerDateStart != null && registerDateEnd != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION)
                            , startDateTime, endDateTime)
            );
        } else if (registerDateStart != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION)
                            , startDateTime, LocalDateTime.now())
            );
        } else if (registerDateEnd != null) {
            predicates.add(
                    cb.between(root.get(Users_.DATE_TIME_SUBMISSION),
                            LocalDateTime.now().minusMonths(2L), endDateTime)
            );
        }
    }


    private void fillRequestCounts(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, Integer minRequests, Integer maxRequests) {
        Path<Object> objectPath = root.join(Users_.ROLES).get(Role_.NAME);
        String customer = RoleNames.CUSTOMER;
        boolean equals = root.join(Users_.ROLES).get(Role_.NAME).equals(RoleNames.CUSTOMER);
        if (root.join(Users_.ROLES).get(Role_.NAME).equals(RoleNames.CUSTOMER)) {
            List<UserOrderCount> customerOrderCounts = customerRepository.getCustomerOrderCounts();
            completeRequestCounts(predicates, root, cb, minRequests, maxRequests, customerOrderCounts, true);

        } else if (root.join(Users_.ROLES).get(Role_.NAME).equals(RoleNames.EXPERT)) {
            List<UserOrderCount> expertOrderCounts = expertRepository.getExpertOrderCounts();
            completeRequestCounts(predicates, root, cb, minRequests, maxRequests, expertOrderCounts, false);
        }
    }

    private void completeRequestCounts(List<Predicate> predicates, Root<Users> root, CriteriaBuilder cb,
                                       Integer minRequests, Integer maxRequests, List<UserOrderCount> orderCounts, boolean isCustomer) {
        for (UserOrderCount orderCount : orderCounts) {
            Predicate userPredicate = cb.equal(root.get(Users_.ID), orderCount.getUserId());
            Predicate requestCountPredicate = null;
            Long totalRequests = orderCount.getTotalRequests();

            if (minRequests != null && maxRequests != null) {
                requestCountPredicate = cb.between(
                        cb.literal(totalRequests),
                        cb.literal(minRequests.longValue()),
                        cb.literal(maxRequests.longValue())
                );
            } else if (minRequests != null) {
                requestCountPredicate = cb.greaterThan(
                        cb.literal(totalRequests),
                        cb.literal(minRequests.longValue())
                );
            } else if (maxRequests != null) {
                requestCountPredicate = cb.lessThanOrEqualTo(
                        cb.literal(totalRequests),
                        cb.literal(maxRequests.longValue())
                );
            }
            if (requestCountPredicate != null) {
                predicates.add(cb.and(userPredicate, requestCountPredicate));
            }
        }
    }

    private void fillDoneCounts(List<Predicate> predicates, Root<Users> root, CriteriaBuilder cb,
                                    Integer minDoneCount, Integer maxDoneCount) {
        if (root.join(Users_.ROLES).get(Role_.NAME).equals(RoleNames.CUSTOMER)){
            List<UserOrderCount> customerOrderCounts = customerRepository.getCustomerOrderCounts();
            completeDoneCounts(predicates,root,cb,customerOrderCounts,minDoneCount,maxDoneCount);
        } else if (root.join(Users_.ROLES).get(Role_.NAME).equals(RoleNames.EXPERT)){
            List<UserOrderCount> expertOrderCounts = expertRepository.getExpertOrderCounts();
            completeDoneCounts(predicates,root,cb,expertOrderCounts,minDoneCount,maxDoneCount);
        }
    }

    private void completeDoneCounts(List<Predicate> predicates,Root<Users> root,CriteriaBuilder cb,List<UserOrderCount> orderCounts
            ,Integer minDoneCount,Integer maxDoneCount){
        for (UserOrderCount orderCount : orderCounts) {
            Predicate userPredicate = cb.equal(root.get(Users_.ID), orderCount.getUserId());
            Predicate doneCountPredicate = null;
            Long doneRequests = orderCount.getDoneOrders();

            if (minDoneCount != null && maxDoneCount != null) {
                doneCountPredicate = cb.between(
                        cb.literal(doneRequests),
                        cb.literal(minDoneCount.longValue()),
                        cb.literal(maxDoneCount.longValue())
                );
            } else if (minDoneCount != null) {
                doneCountPredicate = cb.greaterThan(
                        cb.literal(doneRequests),
                        cb.literal(minDoneCount.longValue())
                );
            } else if (maxDoneCount != null) {
                doneCountPredicate = cb.lessThanOrEqualTo(
                        cb.literal(doneRequests),
                        cb.literal(maxDoneCount.longValue())
                );
            }
            if (doneCountPredicate != null) {
                predicates.add(cb.and(userPredicate, doneCountPredicate));
            }
        }
    }
}
