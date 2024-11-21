package application.service.impl;

import application.constants.RoleNames;
import application.dto.UserOrderCountReportDto;
import application.dto.UserReportFilterAdmin;
import application.dto.projection.UserOrderCount;
import application.entity.Role;
import application.entity.Role_;
import application.entity.users.Users;
import application.entity.users.Users_;
import application.mapper.UserOrderReportMapper;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
import application.repository.UserRepository;
import application.service.UserRequestSpecification;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserRequestSpecificationImpl implements UserRequestSpecification {
    private final UserRepository<Users> userRepository;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;
    private final UserOrderReportMapper userOrderReportMapper;

    @Override
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
        List<UserOrderCount> orderCountsCustomer = getOrderCountsForCustomer(users);
        List<UserOrderCount> orderCountsExpert = getOrderCountsForExpert(users);
        List<UserOrderCountReportDto> dtoList = new ArrayList<>();
        for (Users user : users) {
            Set<Role> roles = user.getRoles();

            if (roles.stream().anyMatch(role -> role.getName().equals(RoleNames.CUSTOMER))) {
                Optional<UserOrderCount> orderCountCustomer = findOrderCountForUser(user.getId(), orderCountsCustomer);
                UserOrderCountReportDto dtoCustomer = userOrderReportMapper.convertEntityToDto(user, orderCountCustomer);
                dtoList.add(dtoCustomer);
            } else if (roles.stream().anyMatch(role -> role.getName().equals(RoleNames.EXPERT))) {
                Optional<UserOrderCount> orderCountExpert = findOrderCountForUser(user.getId(), orderCountsExpert);
                UserOrderCountReportDto dtoExpert = userOrderReportMapper.convertEntityToDto(user, orderCountExpert);
                dtoList.add(dtoExpert);
            }
        }
        return dtoList;
    }


    private List<UserOrderCount> getOrderCountsForCustomer(List<Users> users) {
        return customerRepository.getCustomerOrderCounts();
    }

    private List<UserOrderCount> getOrderCountsForExpert(List<Users> users) {
        return expertRepository.getExpertOrderCounts();
    }

    private Optional<UserOrderCount> findOrderCountForUser(Integer userId, List<UserOrderCount> orderCounts) {
        return orderCounts.stream()
                .filter(orderCount -> orderCount.getUserId().equals(userId))
                .findFirst();
    }


    private void fillRegistrationDate(List<Predicate> predicates, Root<Users> root
            , CriteriaBuilder cb, String registerDateStart, String registerDateEnd) {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (registerDateStart != null) {
            startDateTime = LocalDateTime.parse(registerDateStart);
        }
        if (registerDateEnd != null) {
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


    private void fillRequestCounts(List<Predicate> predicates, Root<Users> root, CriteriaBuilder cb,
                                   Integer minRequests, Integer maxRequests) {
        List<UserOrderCount> customerOrderCounts = customerRepository.getCustomerOrderCounts();
        List<UserOrderCount> expertOrderCounts = expertRepository.getExpertOrderCounts();
        List<Predicate> requestPredicates = new ArrayList<>();
        createRequestPredicates(requestPredicates, cb, customerOrderCounts, minRequests, maxRequests, root);
        createRequestPredicates(requestPredicates, cb, expertOrderCounts, minRequests, maxRequests, root);

        if (!requestPredicates.isEmpty()) {
            predicates.add(cb.or(requestPredicates.toArray(new Predicate[0])));
        }
    }

    private void createRequestPredicates(List<Predicate> requestPredicates, CriteriaBuilder cb,
                                         List<UserOrderCount> orderCounts, Integer minRequests,
                                         Integer maxRequests, Root<Users> root) {
        for (UserOrderCount orderCount : orderCounts) {
            Expression<Long> totalRequestsExpr = cb.literal(orderCount.getTotalRequests());
            Predicate userPredicate = cb.equal(root.get(Users_.ID), orderCount.getUserId());

            Predicate countPredicate = null;

            if (minRequests != null && maxRequests != null) {
                countPredicate = cb.between(totalRequestsExpr, minRequests.longValue(), maxRequests.longValue());
            } else if (minRequests != null) {
                countPredicate = cb.greaterThanOrEqualTo(totalRequestsExpr, minRequests.longValue());
            } else if (maxRequests != null) {
                countPredicate = cb.lessThanOrEqualTo(totalRequestsExpr, maxRequests.longValue());
            }

            if (countPredicate != null) {
                requestPredicates.add(cb.and(userPredicate, countPredicate));
            }
        }
    }

    private void fillDoneCounts(List<Predicate> predicates, Root<Users> root, CriteriaBuilder cb,
                                Integer minDoneCount, Integer maxDoneCount) {
        List<UserOrderCount> customerOrderCounts = customerRepository.getCustomerOrderCounts();
        List<UserOrderCount> expertOrderCounts = expertRepository.getExpertOrderCounts();
        List<Predicate> doneCountsPredicates = new ArrayList<>();
        createDoneOrderPredicates(doneCountsPredicates, cb, customerOrderCounts, minDoneCount, maxDoneCount, root);
        createDoneOrderPredicates(doneCountsPredicates, cb, expertOrderCounts, minDoneCount, maxDoneCount, root);

        if (!doneCountsPredicates.isEmpty()) {
            predicates.add(cb.or(doneCountsPredicates.toArray(new Predicate[0])));
        }
    }

    private void createDoneOrderPredicates(List<Predicate> doneCountsPredicates, CriteriaBuilder cb,
                                           List<UserOrderCount> orderCounts, Integer minDoneCount,
                                           Integer maxDoneCount, Root<Users> root) {
        for (UserOrderCount orderCount : orderCounts) {
            Expression<Long> doneOrderExpr = cb.literal(orderCount.getDoneOrders());
            Predicate userPredicate = cb.equal(root.get(Users_.ID), orderCount.getUserId());

            Predicate countPredicate = null;

            if (minDoneCount != null && maxDoneCount != null) {
                countPredicate = cb.between(doneOrderExpr, minDoneCount.longValue(), maxDoneCount.longValue());
            } else if (minDoneCount != null) {
                countPredicate = cb.greaterThanOrEqualTo(doneOrderExpr, minDoneCount.longValue());
            } else if (maxDoneCount != null) {
                countPredicate = cb.lessThanOrEqualTo(doneOrderExpr, maxDoneCount.longValue());
            }

            if (countPredicate != null) {
                doneCountsPredicates.add(cb.and(userPredicate, countPredicate));
            }
        }
    }

}
