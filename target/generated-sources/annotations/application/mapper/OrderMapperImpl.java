package application.mapper;

import application.dto.OrderResponseDto;
import application.entity.Duty;
import application.entity.Offer;
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
    date = "2024-10-21T08:17:16+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order convertDtoToEntity(OrderResponseDto d) {
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
        Set<Offer> set = d.offers();
        if ( set != null ) {
            order.offers( new LinkedHashSet<Offer>( set ) );
        }

        return order.build();
    }

    @Override
    public List<Order> convertDtoToEntity(List<OrderResponseDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( d.size() );
        for ( OrderResponseDto orderResponseDto : d ) {
            list.add( convertDtoToEntity( orderResponseDto ) );
        }

        return list;
    }

    @Override
    public List<OrderResponseDto> convertEntityToDto(List<Order> e) {
        if ( e == null ) {
            return null;
        }

        List<OrderResponseDto> list = new ArrayList<OrderResponseDto>( e.size() );
        for ( Order order : e ) {
            list.add( convertEntityToDto( order ) );
        }

        return list;
    }

    @Override
    public OrderResponseDto convertEntityToDto(Order order) {
        if ( order == null ) {
            return null;
        }

        Integer customerId = null;
        Integer dutyId = null;
        Integer expertId = null;
        String description = null;
        Integer priceOrder = null;
        String dateTimeOrderForDoingFromCustomer = null;
        String orderStatus = null;
        Set<Offer> offers = null;

        customerId = orderCustomerId( order );
        dutyId = orderDutyId( order );
        expertId = orderExpertId( order );
        description = order.getDescription();
        priceOrder = order.getPriceOrder();
        if ( order.getDateTimeOrderForDoingFromCustomer() != null ) {
            dateTimeOrderForDoingFromCustomer = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( order.getDateTimeOrderForDoingFromCustomer() );
        }
        if ( order.getOrderStatus() != null ) {
            orderStatus = order.getOrderStatus().name();
        }
        Set<Offer> set = order.getOffers();
        if ( set != null ) {
            offers = new LinkedHashSet<Offer>( set );
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto( description, priceOrder, dateTimeOrderForDoingFromCustomer, orderStatus, customerId, expertId, dutyId, offers );

        return orderResponseDto;
    }

    @Override
    public Set<OrderResponseDto> convertEntityToDto(Set<Order> e) {
        if ( e == null ) {
            return null;
        }

        Set<OrderResponseDto> set = new LinkedHashSet<OrderResponseDto>( Math.max( (int) ( e.size() / .75f ) + 1, 16 ) );
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
