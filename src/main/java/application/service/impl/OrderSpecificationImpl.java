package application.service.impl;


import application.dto.ResponseSearchOrderDto;
import application.dto.SearchOrderDto;
import application.entity.Duty_;
import application.entity.Order;
import application.entity.Order_;
import application.repository.OrderRepository;
import application.service.OrderSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderSpecificationImpl implements OrderSpecification {

    private final OrderRepository repository;

    @Override
    public List<ResponseSearchOrderDto> findAllBySearchOrderDto(SearchOrderDto searchDto) {
        List<Order> orders = repository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    fillOrderStatus(predicates, root, cb, searchDto.getOrderStatus());
                    fillDutyId(predicates, root, cb, searchDto.getDutyId());
                    fillDate(predicates, root, cb, searchDto.getStartDate(), searchDto.getEndDate());
                    return cb.and(predicates.toArray(new Predicate[0]));
                });
        return mapOrdersToResponseSearchDto(orders);
    }

    private List<ResponseSearchOrderDto> mapOrdersToResponseSearchDto(List<Order> orders) {
        List<ResponseSearchOrderDto> responseOrders = new ArrayList<>();
        orders.forEach(
                order ->
                        responseOrders.add(
                        ResponseSearchOrderDto.builder()
                                .orderId(order.getId())
                                .priceOrder(order.getPriceOrder())
                                .orderDateCreation(order.getOrderDateCreation())
                                .dateTimeOrderForDoingFromCustomer(order.getDateTimeOrderForDoingFromCustomer())
                                .orderStatus(order.getOrderStatus())
                                .customerId(order.getCustomer()!=null?order.getCustomer().getId():null)
                                .expertId(order.getExpert()!=null?order.getExpert().getId():null)
                                .dutyId(order.getDuty().getId())
                                .countOffers(order.getOffers()!=null?order.getOffers().size():0)
                                .doneUpdate(order.getDoneUpdate()!=null?order.getDoneUpdate():null)
                                .build()
                )
        );
        return responseOrders;
    }

    private void fillDate(List<Predicate> predicates, Root<Order> root, CriteriaBuilder cb, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(root.get(Order_.ORDER_DATE_CREATION), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.between(root.get(Order_.ORDER_DATE_CREATION), startDate, LocalDate.now()));
        } else if (endDate != null) {
            predicates.add(cb.between(root.get(Order_.ORDER_DATE_CREATION), LocalDate.now().minusMonths(3), endDate));
        }
    }

    private void fillOrderStatus(List<Predicate> predicates, Root<Order> root, CriteriaBuilder cb, String orderStatus) {
        if (StringUtils.isNotBlank(orderStatus)) {
            predicates.add(
                    cb.equal(root.get(Order_.ORDER_STATUS),
                            orderStatus)
            );
        }
    }

    private void fillDutyId(List<Predicate> predicates, Root<Order> root, CriteriaBuilder cb, Set<Integer> dutyId) {
        if (dutyId != null && !dutyId.isEmpty()) {
            List<Predicate> orPredicate = new ArrayList<>();
            for (int id : dutyId) {
                orPredicate.add(
                        cb.equal(root.get(Order_.DUTY).get(Duty_.ID),
                                id)
                );
            }
            predicates.add(cb.or(orPredicate.toArray(new Predicate[0])));
        }
    }

}