package application.mapper;

import application.dto.OrderResponseDto;
import application.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderResponseDto> {


    @Mappings({
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "dutyId", source = "duty.id"),
            @Mapping(target = "expertId", source = "expert.id")
    }
    )
    OrderResponseDto convertEntityToDto(Order order);


    List<OrderResponseDto> convertEntityToDto(List<Order> e);
}
