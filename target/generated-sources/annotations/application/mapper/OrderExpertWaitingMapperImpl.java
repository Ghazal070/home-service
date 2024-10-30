package application.mapper;

import application.dto.OrderExpertWaitingDto;
import application.entity.Duty;
import application.entity.Order;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.entity.users.Expert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-30T08:40:43+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class OrderExpertWaitingMapperImpl implements OrderExpertWaitingMapper {

    @Override
    public Order convertDtoToEntity(OrderExpertWaitingDto d) {
        if ( d == null ) {
            return null;
        }

        Order.OrderBuilder<?, ?> order = Order.builder();

        order.description( d.description() );
        order.priceOrder( d.priceOrder() );
        if ( d.dateTimeOrderForDoingFromCustomer() != null ) {
            order.dateTimeOrderForDoingFromCustomer( LocalDateTime.parse( d.dateTimeOrderForDoingFromCustomer() ) );
        }
        if ( d.orderStatus() != null ) {
            order.orderStatus( Enum.valueOf( OrderStatus.class, d.orderStatus() ) );
        }

        return order.build();
    }

    @Override
    public List<Order> convertDtoToEntity(List<OrderExpertWaitingDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( d.size() );
        for ( OrderExpertWaitingDto orderExpertWaitingDto : d ) {
            list.add( convertDtoToEntity( orderExpertWaitingDto ) );
        }

        return list;
    }

    @Override
    public List<OrderExpertWaitingDto> convertEntityToDto(List<Order> e) {
        if ( e == null ) {
            return null;
        }

        List<OrderExpertWaitingDto> list = new ArrayList<OrderExpertWaitingDto>( e.size() );
        for ( Order order : e ) {
            list.add( convertEntityToDto( order ) );
        }

        return list;
    }

    @Override
    public OrderExpertWaitingDto convertEntityToDto(Order order) {
        if ( order == null ) {
            return null;
        }

        Integer customerId = null;
        Integer dutyId = null;
        Integer expertId = null;
        Integer orderId = null;
        String description = null;
        Integer priceOrder = null;
        String dateTimeOrderForDoingFromCustomer = null;
        String orderStatus = null;

        customerId = orderCustomerId( order );
        dutyId = orderDutyId( order );
        expertId = orderExpertId( order );
        orderId = order.getId();
        description = order.getDescription();
        priceOrder = order.getPriceOrder();
        if ( order.getDateTimeOrderForDoingFromCustomer() != null ) {
            dateTimeOrderForDoingFromCustomer = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( order.getDateTimeOrderForDoingFromCustomer() );
        }
        if ( order.getOrderStatus() != null ) {
            orderStatus = order.getOrderStatus().name();
        }

        OrderExpertWaitingDto orderExpertWaitingDto = new OrderExpertWaitingDto( orderId, description, priceOrder, dateTimeOrderForDoingFromCustomer, orderStatus, customerId, expertId, dutyId );

        return orderExpertWaitingDto;
    }

    @Override
    public Set<OrderExpertWaitingDto> convertEntityToDto(Set<Order> e) {
        if ( e == null ) {
            return null;
        }

        Set<OrderExpertWaitingDto> set = new LinkedHashSet<OrderExpertWaitingDto>( Math.max( (int) ( e.size() / .75f ) + 1, 16 ) );
        for ( Order order : e ) {
            set.add( convertEntityToDto( order ) );
        }

        return set;
    }

    private Integer orderCustomerId(Order order) {
        Customer customer = order.getCustomer();
        if ( customer == null ) {
            return null;
        }
        return customer.getId();
    }

    private Integer orderDutyId(Order order) {
        Duty duty = order.getDuty();
        if ( duty == null ) {
            return null;
        }
        return duty.getId();
    }

    private Integer orderExpertId(Order order) {
        Expert expert = order.getExpert();
        if ( expert == null ) {
            return null;
        }
        return expert.getId();
    }
}
