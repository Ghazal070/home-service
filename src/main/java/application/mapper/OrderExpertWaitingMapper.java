package application.mapper;

import application.dto.OrderExpertWaitingDto;
import application.dto.OrderResponseDto;
import application.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderExpertWaitingMapper extends BaseMapper<Order, OrderExpertWaitingDto> {


    @Mappings({
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "dutyId", source = "duty.id"),
            @Mapping(target = "expertId", source = "expert.id"),
            @Mapping(target = "orderId", source = "id")

    }
    )
    OrderExpertWaitingDto convertEntityToDto(Order order);


    @Mappings({
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "dutyId", source = "duty.id"),
            @Mapping(target = "expertId", source = "expert.id"),
            @Mapping(target = "orderId", source = "id")
    }
    )
    Set<OrderExpertWaitingDto> convertEntityToDto(Set<Order> e);
}
